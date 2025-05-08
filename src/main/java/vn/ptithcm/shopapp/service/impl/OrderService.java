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
    IAddressService addressService;
    ProductRepository productRepository;
    FilterParser filterParser;
    FilterSpecificationConverter filterSpecificationConverter;
    SecurityUtil securityUtil;



    @Override
    @Transactional
    public OrderResponseDTO handleCreateOrder(CreateOrderRequestDTO orderRequest, User userOrder) {

        validateOrderRequest(orderRequest);

        Order order = saveOrder(orderRequest, userOrder);

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

        order.setStatus(ordRequest.getStatus());

//        if (ordRequest.getAddress().getId() != order.getAddress().getId()) {
//            Address addressDB = addressService.handleFetchAddressById(ordRequest.getAddress().getId());
//
//            if(addressDB.getUser().getId() != user.getId()){
//                throw new IdInvalidException("Invalid address");
//            }
//            order.setAddress(addressDB);
//        }
        validateAndUpdateOrderAddress(order, ordRequest.getAddress().getId(), user.getId());
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

        order.setStatus(ordRequest.getStatus());

//        if (ordRequest.getAddress().getId() != order.getAddress().getId()) {
//            Address addressDB = addressService.handleFetchAddressById(ordRequest.getAddress().getId());
//
//            if(addressDB.getUser().getId() != order.getUser().getId()){
//                throw new IdInvalidException("Invalid address");
//            }
//            order.setAddress(addressDB);
//        }
        validateAndUpdateOrderAddress(order, ordRequest.getAddress().getId(), order.getUser().getId());

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



    private void validateOrderRequest(CreateOrderRequestDTO orderRequest) {
        if (orderRequest.getOrderDetails().isEmpty()) {
            throw new IdInvalidException("Order Detail might be empty. Please add a product!!!");
        }
    }

    private Order saveOrder(CreateOrderRequestDTO orderRequest, User userOrder) {

        Address address = null;
        if(orderRequest.getAddress() != null && orderRequest.getAddress().getId() != null) {
            address = addressService.handleFetchAddressById(orderRequest.getAddress().getId());

            if (address.getUser().getId() != userOrder.getId()) {
                throw new IdInvalidException("Users don't have the same address");
            }
        }

        Order order = orderConverter.convertToOrder(orderRequest);
        order.setAddress(address);
        order.setUser(userOrder);
        return orderRepository.save(order);
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
    }
    private void validateAndUpdateOrderAddress(Order order, Long newAddressId, Long userId) {
        if (!order.getAddress().getId().equals(newAddressId)) {
            Address addressDB = addressService.handleFetchAddressById(newAddressId);

            if (!addressDB.getUser().getId().equals(userId)) {
                throw new IdInvalidException("Invalid address");
            }
            order.setAddress(addressDB);
        }
    }


}
