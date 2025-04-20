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
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.request.UpdateOrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;
import vn.ptithcm.shopapp.repository.*;
import vn.ptithcm.shopapp.service.IOrderService;
import vn.ptithcm.shopapp.util.PaginationUtil;
import vn.ptithcm.shopapp.util.SecurityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    OrderRepository orderRepository;
    OrderConverter orderConverter;
    PaymentRepository paymentRepository;
    ProductService productService;
    OrderDetailRepository orderDetailRepository;
    UserService userService;
    ProductRepository productRepository;
    FilterParser filterParser;
    FilterSpecificationConverter filterSpecificationConverter;



    @Override
    @Transactional
    public OrderResponseDTO handleCreateOrder(OrderRequestDTO orderRequest, User userOrder) {

        validateOrderRequest(orderRequest);

        Order order = saveOrder(orderRequest, userOrder);

        savePayment(orderRequest.getAmountPaid(),order);

        processOrderDetails(orderRequest, order);

        return orderConverter.convertToOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO handleUpdateOrder(UpdateOrderRequestDTO ordRequest) {

        Order order = handleFetchOrder(ordRequest.getId());


        String currentUserRole = userService.getUserLogin().getRole().getCode();

        if (currentUserRole.equalsIgnoreCase(SecurityUtil.ROLE_CUSTOMER)
                && (!ordRequest.getStatus().equals(OrderStatusEnum.UNPAID) && !ordRequest.getStatus().equals(OrderStatusEnum.PENDING))
        ) {
            ordRequest.setStatus(order.getStatus());
        }

        if((!order.getStatus().equals(OrderStatusEnum.PAID) && !order.getStatus().equals(OrderStatusEnum.PENDING))
                && !order.getShippingAddress().equals(ordRequest.getShippingAddress())
                && !order.getPhone().equals(ordRequest.getPhone())
                && !order.getName().equals(ordRequest.getName())
            ){
            throw new IdInvalidException("Can not update shipping information for your status");
        }

        orderConverter.updateOrder(order, ordRequest);

        if(ordRequest.getAmountPaid()!= null  && !currentUserRole.equalsIgnoreCase(SecurityUtil.ROLE_CUSTOMER)){
            savePayment(ordRequest.getAmountPaid(), order);
        }

        orderRepository.save(order);

        return orderConverter.convertToOrderResponseDTO(order);
    }

    @Override
    public Order handleFetchOrder(Long id) {
        Order orderDB = orderRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Order with id " + id + " not found"));

        return orderDB;
    }

    @Override
    public OrderResponseDTO handleFetchOrderResponse(Long id) {

        var order = orderRepository.findById(id).orElseThrow(() -> new IdInvalidException(id+" not already"));
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

        if(currentUserLogin.getId() != userDB.getId() && currentUserLogin.getRole().getCode().equalsIgnoreCase(SecurityUtil.ROLE_CUSTOMER)){
            throw new IdInvalidException("Access denied");
        }
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


        result.setResult(orders.getContent());

        return result;
    }

    private double getTotalPaid(String orderId) {
        return paymentRepository.sumPaymentsByOrderId(orderId);
    }

    private void validateOrderRequest(OrderRequestDTO orderRequest) {
        if (orderRequest.getOrderDetails().isEmpty()) {
            throw new IdInvalidException("Order Detail might be empty. Please add a product!!!");
        }
    }

    private Order saveOrder(OrderRequestDTO orderRequest, User userOrder) {

        Order order = orderConverter.convertToOrder(orderRequest);
        order.setUser(userOrder);
        return orderRepository.save(order);
    }

    private void savePayment(Double amountPaid, Order order) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmountPaid(amountPaid != null ? amountPaid : 0.0);
        paymentRepository.save(payment);
    }

    private void processOrderDetails(OrderRequestDTO orderRequest, Order order) {
        List<String> productIds = orderRequest.getOrderDetails().stream()
                .map(OrderRequestDTO.OrderDetails::getProductId)
                .toList();

        List<Product> products = productService.handleFetchAllProductByIds(productIds);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        it -> it
                ));

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderRequestDTO.OrderDetails detail : orderRequest.getOrderDetails()) {
            Product product = productMap.get(detail.getProductId());

            if (product.getQuantity() < detail.getQuantity() || !product.getStatus()) {
                throw new IdInvalidException("Product " + product.getName() + " is out of stock.");
            }
            product.setQuantity(product.getQuantity() - detail.getQuantity());
            product.setSold(product.getSold() + detail.getQuantity());
            product.setShouldUpdateAudit(false);

            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setPrice(detail.getPrice());
            orderDetail.setQuantity(detail.getQuantity());
            orderDetail.setProduct(product);

            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);

        productRepository.saveAll(products);
        orderDetailRepository.saveAll(orderDetails);
    }
}
