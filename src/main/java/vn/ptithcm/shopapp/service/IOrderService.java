package vn.ptithcm.shopapp.service;

import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;

public interface IOrderService {

    OrderResponseDTO handleCreateCustomerOrder(OrderRequestDTO orderRequest);

    OrderResponseDTO handleEmployeeCreateOrder(OrderRequestDTO orderRequest);
}
