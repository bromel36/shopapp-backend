package vn.ptithcm.shopapp.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptithcm.shopapp.converter.OrderConverter;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.*;
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;
import vn.ptithcm.shopapp.repository.OrderRepository;
import vn.ptithcm.shopapp.repository.PaymentRepository;
import vn.ptithcm.shopapp.service.IOrderService;
import vn.ptithcm.shopapp.service.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final IUserService userService;
    private final OrderConverter orderConverter;
    private final PaymentRepository paymentRepository;
    private final ProductService productService;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, IUserService userService, OrderConverter orderConverter, PaymentRepository paymentRepository, ProductService productService,
                        OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.orderConverter = orderConverter;
        this.paymentRepository = paymentRepository;
        this.productService = productService;
        this.orderDetailRepository = orderDetailRepository;
    }


    @Override
    @Transactional
    public OrderResponseDTO handleCreateOrder(OrderRequestDTO orderRequest) {
        User userDB = userService.getUserLogin();

        Order order = orderConverter.convertToOrder(orderRequest);

        order.setUser(userDB);

        orderRepository.save(order);

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmountPaid(orderRequest.getAmountPaid());

        paymentRepository.save(payment);

        if(orderRequest.getOrderDetails().isEmpty()){
           throw new IdInvalidException("Order Detail might be empty. Please a product!!!");
        }

        List<String> productIds = orderRequest.getOrderDetails().stream().map(OrderRequestDTO.OrderDetails::getProductId).toList();

        List<Product> products = productService.handleFetchAllProductByIds(productIds);

        Map<String, Product> orderProductsMap = products.stream().collect(Collectors.toMap(
                Base::getId,
                it->it
        ));

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderRequest.getOrderDetails().forEach(orderDetail -> {
            OrderDetail ord = new OrderDetail();

            ord.setOrder(order);
            ord.setPrice(orderDetail.getPrice());
            ord.setQuantity(orderDetail.getQuantity());
            ord.setProduct(orderProductsMap.get(orderDetail.getProductId()));

            //còn xử lý product instance nhma tạm thời chưa xử lý

            orderDetails.add(ord);
        });

        orderDetailRepository.saveAll(orderDetails);

    }
}
