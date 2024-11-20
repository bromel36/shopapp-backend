package vn.ptithcm.shopapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.ptithcm.shopapp.model.entity.Order;
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;

@Component
public class OrderConverter {

    private final ModelMapper  modelMapper;

    public OrderConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Order convertToOrder(OrderRequestDTO dto){
        Order order = modelMapper.map(dto, Order.class);

        return order;
    }

//    public OrderResponseDTO convertToOrderResponseDTO(Order order){
//        OrderResponseDTO dto = modelMapper.map(order, OrderResponseDTO.class);
//
//        if(order.getCustomer()!= null){
//            OrderResponseDTO.CustomerOrder customerOrder = new OrderResponseDTO.CustomerOrder();
//
//            customerOrder.setId(order.getCustomer().getId());
//            dto.setCustomer(customerOrder);
//        }
//
//        if(order.getEmployee()!= null){
//            OrderResponseDTO.EmployeeOrder employeeOrder = new OrderResponseDTO.EmployeeOrder();
//            employeeOrder.setId(order.getEmployee().getId());
//            dto.setEmployee(employeeOrder);
//        }
//
//        return dto;
//    }

}
