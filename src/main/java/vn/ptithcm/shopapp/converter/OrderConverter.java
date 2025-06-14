package vn.ptithcm.shopapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.ptithcm.shopapp.enums.OrderStatusEnum;
import vn.ptithcm.shopapp.enums.PaymentMethodEnum;
import vn.ptithcm.shopapp.model.entity.Order;
import vn.ptithcm.shopapp.model.request.CreateOrderRequestDTO;
import vn.ptithcm.shopapp.model.request.UpdateOrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;

@Component
public class OrderConverter {

    private final ModelMapper modelMapper;

    public OrderConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void convertToOrder(Order order, CreateOrderRequestDTO dto) {


        if(dto.getStatus() == null){
            order.setStatus(OrderStatusEnum.PENDING);
        }
        else{
            order.setStatus(dto.getStatus());
        }

        order.setPaymentMethod(dto.getPaymentMethod());
        order.setName(dto.getName());
        order.setShippingAddress(dto.getShippingAddress());
        order.setPhone(dto.getPhone());



    }

    public OrderResponseDTO convertToOrderResponseDTO(Order order) {
        OrderResponseDTO dto = modelMapper.map(order, OrderResponseDTO.class);

        return dto;
    }

    public void updateOrder(Order order, UpdateOrderRequestDTO dto) {
        modelMapper.map(dto, order);
    }

}



























