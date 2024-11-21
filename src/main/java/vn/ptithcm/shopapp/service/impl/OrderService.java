package vn.ptithcm.shopapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptithcm.shopapp.converter.OrderConverter;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.*;
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.request.UpdateOrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;
import vn.ptithcm.shopapp.repository.OrderRepository;
import vn.ptithcm.shopapp.repository.PaymentRepository;
import vn.ptithcm.shopapp.repository.ProductRepository;
import vn.ptithcm.shopapp.repository.UserRepository;
import vn.ptithcm.shopapp.service.IOrderService;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.SecurityUtil;
import vn.ptithcm.shopapp.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final PaymentRepository paymentRepository;
    private final ProductService productService;
    private final OrderDetailRepository orderDetailRepository;
    private final UserService userService;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, OrderConverter orderConverter, PaymentRepository paymentRepository, ProductService productService,
                        OrderDetailRepository orderDetailRepository, UserService userService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.paymentRepository = paymentRepository;
        this.productService = productService;
        this.orderDetailRepository = orderDetailRepository;
        this.userService = userService;
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public OrderResponseDTO handleCreateOrder(OrderRequestDTO orderRequest) {

        validateOrderRequest(orderRequest);

        User userOrder = userService.getUserById(orderRequest.getUser().getId());

        User currentUserLogin = userService.getUserLogin();

        validateUserPermissions(currentUserLogin, userOrder);

        Order order = saveOrder(orderRequest, userOrder);

        savePayment(orderRequest.getAmountPaid(),order);

        processOrderDetails(orderRequest, order);

        return orderConverter.convertToOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO handleUpdateOrder(UpdateOrderRequestDTO ordRequest) {

        var order = orderRepository.findById(ordRequest.getId()).orElseThrow(() -> new IdInvalidException(ordRequest.getId()+" not already"));

        orderConverter.updateOrder(order, ordRequest);

        if(ordRequest.getAmountPaid()!= 0  && !userService.getUserLogin().getRole().getCode().equalsIgnoreCase(SecurityUtil.ROLE_CUSTOMER)){
            savePayment(ordRequest.getAmountPaid(), order);
        }

        orderRepository.save(order);

        return orderConverter.convertToOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO handleFetchOrder(String id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new IdInvalidException(id+" not already"));
        return orderConverter.convertToOrderResponseDTO(order);
    }

    private double getTotalPaid(String orderId) {
        return paymentRepository.sumPaymentsByOrderId(orderId);
    }

    private void validateOrderRequest(OrderRequestDTO orderRequest) {
        if (orderRequest.getOrderDetails().isEmpty()) {
            throw new IdInvalidException("Order Detail might be empty. Please add a product!!!");
        }
    }

    private void validateUserPermissions(User currentUser, User orderUser) {
        if (currentUser.getRole().getCode().equalsIgnoreCase(SecurityUtil.ROLE_CUSTOMER)
                && !currentUser.getId().equalsIgnoreCase(orderUser.getId())) {
            throw new IdInvalidException("Customers can only order by themselves.");
        }
        if (!currentUser.getRole().getCode().equalsIgnoreCase(SecurityUtil.ROLE_CUSTOMER)
                && currentUser.getId().equalsIgnoreCase(orderUser.getId())) {
            throw new IdInvalidException("Staff are not allowed to order by themselves.");
        }
    }

    private Order saveOrder(OrderRequestDTO orderRequest, User userOrder) {
        Order order = orderConverter.convertToOrder(orderRequest);
        order.setUser(userOrder);
        return orderRepository.save(order);
    }

    private void savePayment(double amountPaid, Order order) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmountPaid(amountPaid);
        paymentRepository.save(payment);
    }

    private void processOrderDetails(OrderRequestDTO orderRequest, Order order) {
        List<String> productIds = orderRequest.getOrderDetails().stream()
                .map(OrderRequestDTO.OrderDetails::getProductId)
                .toList();

        List<Product> products = productService.handleFetchAllProductByIds(productIds);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Base::getId, Function.identity()));

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderRequestDTO.OrderDetails detail : orderRequest.getOrderDetails()) {
            Product product = productMap.get(detail.getProductId());
            if (product.getQuantity() < detail.getQuantity() || !product.getStatus()) {
                throw new IdInvalidException("Product " + product.getName() + " is out of stock.");
            }
            product.setQuantity(product.getQuantity() - detail.getQuantity());

            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setPrice(detail.getPrice());
            orderDetail.setQuantity(detail.getQuantity());
            orderDetail.setProduct(product);

            orderDetails.add(orderDetail);
        }

        productRepository.saveAll(products);
        orderDetailRepository.saveAll(orderDetails);
    }
}
