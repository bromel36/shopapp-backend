package vn.ptithcm.shopapp.service.impl;

import com.turkraft.springfilter.converter.FilterSpecification;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import com.turkraft.springfilter.parser.node.FilterNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptithcm.shopapp.converter.OrderConverter;
import vn.ptithcm.shopapp.enums.OrderStatusEnum;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.*;
import vn.ptithcm.shopapp.model.request.CreateOrderRequestDTO;
import vn.ptithcm.shopapp.model.request.UpdateOrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.repository.*;
import vn.ptithcm.shopapp.service.IAddressService;
import vn.ptithcm.shopapp.service.IOrderService;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.PaginationUtil;
import vn.ptithcm.shopapp.util.SecurityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    OrderRepository orderRepository;
    OrderConverter orderConverter;
    ProductService productService;
    OrderDetailRepository orderDetailRepository;
    IUserService userService;
    FilterParser filterParser;
    FilterSpecificationConverter filterSpecificationConverter;
    SecurityUtil securityUtil;



    @Override
    @Transactional
    public OrderResponseDTO handleCreateOrder(CreateOrderRequestDTO orderRequest, User userOrder) {

        Order order = new Order();

        orderConverter.convertToOrder(order, orderRequest);
        order.setUser(userOrder);

        processOrderDetails(orderRequest, order);

        return orderConverter.convertToOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO handleCustomerUpdateOrder(UpdateOrderRequestDTO ordRequest, User user) {

        Order order = handleFetchOrder(ordRequest.getId());

        if (order.getUser().getId() != user.getId()){
            throw new IdInvalidException("Customers only update their orders");
        }

        if(!order.getStatus().equals(OrderStatusEnum.PENDING) && !order.getStatus().equals(OrderStatusEnum.UNPAID)) {
            throw new IdInvalidException("Customers does not update this order status");
        }
        else if(order.getStatus().equals(OrderStatusEnum.PENDING) && !ordRequest.getStatus().equals(OrderStatusEnum.CANCELED)) {
            throw new IdInvalidException("Customers does not update this order status");
        }
        else if(order.getStatus().equals(OrderStatusEnum.UNPAID) && !ordRequest.getStatus().equals(OrderStatusEnum.PAID)) {
            throw new IdInvalidException("Customers does not update this order status");
        }

        if(order.getStatus().equals(OrderStatusEnum.PENDING)) {
            order.setName(ordRequest.getName() == null ? order.getName() : ordRequest.getName());
            order.setPhone(ordRequest.getPhone() == null ? order.getPhone() : ordRequest.getPhone());
            order.setShippingAddress(ordRequest.getShippingAddress() == null ? order.getShippingAddress() : ordRequest.getShippingAddress());
        }

        order.setStatus(ordRequest.getStatus());

        orderRepository.save(order);
        return orderConverter.convertToOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO handleAdminUpdateOrder(UpdateOrderRequestDTO ordRequest) {
        Order order = handleFetchOrder(ordRequest.getId());

        if(order.getStatus().equals(OrderStatusEnum.PAID)){
            if (ordRequest.getStatus().equals(OrderStatusEnum.UNPAID) || ordRequest.getStatus().equals(OrderStatusEnum.PENDING)) {
                throw new IdInvalidException("Users does not update this order status to previous state");
            }
        }

        if(ordRequest.getStatus().equals(OrderStatusEnum.PENDING)) {
            order.setName(ordRequest.getName() == null ? order.getName() : ordRequest.getName());
            order.setPhone(ordRequest.getPhone() == null ? order.getPhone() : ordRequest.getPhone());
            order.setShippingAddress(ordRequest.getShippingAddress() == null ? order.getShippingAddress() : ordRequest.getShippingAddress());
        }

        order.setStatus(ordRequest.getStatus());

        orderRepository.save(order);
        return orderConverter.convertToOrderResponseDTO(order);
    }

    @Override
    public void updateOrderStatusById(Long id, OrderStatusEnum status) {
        Order orderDB = handleFetchOrder(id);
        orderDB.setStatus(status);

        orderRepository.save(orderDB);
    }

    @Override
    public Order handleFetchOrder(Long id) {
        Order orderDB = orderRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Order with id " + id + " not found"));

        return orderDB;
    }

    @Override
    public OrderResponseDTO handleFetchOrderResponse(Long id) {

        Order order = orderRepository.findById(id).orElseThrow(() -> new IdInvalidException(id+" not already"));

        User currentUserLogin = userService.getUserLogin();

        securityUtil.checkCustomerIdAccess(order.getUser().getId(), currentUserLogin);

        OrderResponseDTO responseDTO = orderConverter.convertToOrderResponseDTO(order);

        return responseDTO;
    }

    @Override
    public PaginationResponseDTO handleFetchOrderByUserId(Long id, Pageable pageable) {
        User userDB = userService.getUserById(id);
        if(userDB== null){
            throw new IdInvalidException("User not found");
        }

        User currentUserLogin = userService.getUserLogin();

        securityUtil.checkCustomerIdAccess(userDB.getId(), currentUserLogin);

        String filter = "user = '" + id + "' ";
        FilterNode node = filterParser.parse(filter);
        FilterSpecification<Order>  spec = filterSpecificationConverter.convert(filter);

        Page<Order> orders = orderRepository.findAll(spec, pageable);

        PaginationResponseDTO dto = PaginationUtil.handlePaginate(pageable, orders);

        List<OrderResponseDTO> orderResponseDTOS = orders.getContent().stream().map(orderConverter::convertToOrderResponseDTO).toList();

        dto.setResult(orderResponseDTOS);
        return dto;
    }

    @Override
    public PaginationResponseDTO handlFetchAllOrders(Specification<Order> spec, Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(spec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable, orders);

        List<OrderResponseDTO> orderResponseDTOS = orders.getContent().stream().map(orderConverter::convertToOrderResponseDTO).toList();

        result.setResult(orderResponseDTOS);

        return result;
    }


    private void processOrderDetails(CreateOrderRequestDTO orderRequest, Order order) {
        List<String> productIds = orderRequest.getOrderDetails().stream()
                .map(CreateOrderRequestDTO.OrderDetails::getProductId)
                .toList();

        List<Product> products = productService.handleFetchAllProductByIds(productIds);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        it -> it
                ));

        List<OrderDetail> orderDetails = new ArrayList<>();
        Double totalMoney = 0.0;

        for (CreateOrderRequestDTO.OrderDetails detail : orderRequest.getOrderDetails()) {
            Product product = productMap.get(detail.getProductId());

            if (product.getQuantity() < detail.getQuantity() || !product.getStatus()) {
                throw new IdInvalidException("Product " + product.getName() + " is out of stock.");
            }
            product.setQuantity(product.getQuantity() - detail.getQuantity());
            product.setSold(product.getSold() + detail.getQuantity());
            product.setShouldUpdateAudit(false);

            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setPrice(product.getPrice());
            orderDetail.setQuantity(detail.getQuantity());

            totalMoney = totalMoney + product.getPrice()*detail.getQuantity();

            orderDetail.setProduct(product);

            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        order.setTotalMoney(totalMoney);
//        productRepository.saveAll(products);
        orderDetailRepository.saveAll(orderDetails);
        orderRepository.save(order);
    }

}
