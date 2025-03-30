package vn.ptithcm.shopapp.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import vn.ptithcm.shopapp.model.entity.Order;
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.request.UpdateOrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;

public interface IOrderService {

    OrderResponseDTO handleCreateOrder(OrderRequestDTO orderRequest);

    OrderResponseDTO handleUpdateOrder(UpdateOrderRequestDTO ordRequest);

    Order handleFetchOrder(Long id);

    OrderResponseDTO handleFetchOrderResponse(Long id);

    PaginationResponseDTO handleFetchOrderByUserId(Long id, Pageable pageable);
}
