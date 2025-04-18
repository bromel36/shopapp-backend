package vn.ptithcm.shopapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.ptithcm.shopapp.enums.OrderStatusEnum;
import vn.ptithcm.shopapp.model.entity.Order;
import vn.ptithcm.shopapp.model.entity.OrderDetail;
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.request.UpdateOrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConverter {

    private final ModelMapper modelMapper;

    public OrderConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Order convertToOrder(OrderRequestDTO dto) {
        Order order = new Order();

        if (dto.getStatus()== null){
            order.setStatus(OrderStatusEnum.PENDING);
        }
        else{
            order.setStatus(dto.getStatus());
        }
        order.setName(dto.getName());
        order.setPhone(dto.getPhone());
        order.setShippingAddress(dto.getShippingAddress());
        order.setTotalMoney(dto.getTotalMoney());
        order.setPaymentMethod(dto.getPaymentMethod());
// xài modelmapper chỗ này nó sẽ bị lỗi nên phải làm chay
        return order;
    }

    public OrderResponseDTO convertToOrderResponseDTO(Order order) {
        OrderResponseDTO dto = modelMapper.map(order, OrderResponseDTO.class);

//        List<OrderDetail> orderDetails = order.getOrderDetails();
//
//        List<OrderResponseDTO.OrderDetailsResponse> orderDetailsResponses = new ArrayList<>();
//
//        orderDetails.forEach(orderDetail -> {
//            OrderResponseDTO.OrderDetailsResponse ordDetails = new OrderResponseDTO.OrderDetailsResponse();
//
//            ordDetails.setId(orderDetail.getId());
//            ordDetails.setQuantity(orderDetail.getQuantity());
//            ordDetails.setPrice(orderDetail.getPrice());
//            ordDetails.setProductName(orderDetail.getProduct().getName());
//            ordDetails.setProductThumbnail(orderDetail.getProduct().getThumbnail());
//
//            orderDetailsResponses.add(ordDetails);
//        });
//
//        dto.setOrderDetails(orderDetailsResponses);
        return dto;
    }

    public void updateOrder(Order order, UpdateOrderRequestDTO dto) {
        modelMapper.map(dto, order);
    }

}



























