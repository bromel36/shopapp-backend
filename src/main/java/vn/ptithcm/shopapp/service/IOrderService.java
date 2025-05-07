package vn.ptithcm.shopapp.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.enums.OrderStatusEnum;
import vn.ptithcm.shopapp.model.entity.Order;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.CreateOrderRequestDTO;
import vn.ptithcm.shopapp.model.request.UpdateOrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

public interface IOrderService {

    OrderResponseDTO handleCreateOrder(CreateOrderRequestDTO orderRequest, User userOrder);

    OrderResponseDTO handleCustomerUpdateOrder(UpdateOrderRequestDTO ordRequest, User user);

    Order handleFetchOrder(Long id);

    OrderResponseDTO handleFetchOrderResponse(Long id);

    PaginationResponseDTO handleFetchOrderByUserId(Long id, Pageable pageable);

    PaginationResponseDTO handlFetchAllOrders(Specification<Order> spec, Pageable pageable);

    OrderResponseDTO handleAdminUpdateOrder(@Valid UpdateOrderRequestDTO ordRequest);

    void updateOrderStatusById(Long id, OrderStatusEnum status);
}
