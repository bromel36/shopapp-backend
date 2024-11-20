package vn.ptithcm.shopapp.service;

import jakarta.validation.Valid;
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.request.UpdateOrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;

public interface IOrderService {


    OrderResponseDTO handleCreateOrder(OrderRequestDTO orderRequest);

    OrderResponseDTO handleUpdateOrder(UpdateOrderRequestDTO ordRequest);

    OrderResponseDTO handleFetchOrder(String id);
}
