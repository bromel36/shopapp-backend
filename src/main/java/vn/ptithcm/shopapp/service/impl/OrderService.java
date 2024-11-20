package vn.ptithcm.shopapp.service.impl;

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
import vn.ptithcm.shopapp.service.IOrderService;
import vn.ptithcm.shopapp.util.SecurityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


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

        savePayment(orderRequest, order);

        processOrderDetails(orderRequest, order);

        return orderConverter.convertToOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO handleUpdateOrder(UpdateOrderRequestDTO ordRequest) {


        return null;
    }

    @Override
    public Order handleFetchOrder(String id) {
        Order orderDB = orderRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Order with id " + id + " not found"));

        return orderDB;
    }

    @Override
    public OrderResponseDTO handleFetchOrderResponse(String id) {
        Order orderResponse = handleFetchOrder(id);

        return null;
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

    private void savePayment(OrderRequestDTO orderRequest, Order order) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmountPaid(orderRequest.getAmountPaid());
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
