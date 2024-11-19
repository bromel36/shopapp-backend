package vn.ptithcm.shopapp.service.impl;

import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.converter.CustomerConverter;
import vn.ptithcm.shopapp.converter.OrderConverter;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Customer;
import vn.ptithcm.shopapp.model.entity.Employee;
import vn.ptithcm.shopapp.model.entity.Order;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.OrderRequestDTO;
import vn.ptithcm.shopapp.model.response.OrderResponseDTO;
import vn.ptithcm.shopapp.repository.CustomerRepository;
import vn.ptithcm.shopapp.repository.OrderRepository;
import vn.ptithcm.shopapp.service.ICustomerService;
import vn.ptithcm.shopapp.service.IOrderService;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.StringUtil;


@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final IUserService userService;
    private final OrderConverter orderConverter;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, IUserService userService, OrderConverter orderConverter, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.orderConverter = orderConverter;
        this.customerRepository = customerRepository;
    }


    @Override
    public OrderResponseDTO handleCreateCustomerOrder(OrderRequestDTO orderRequest) {
        User userDB = userService.getUserLogin();

        Order toSaveOrder = orderConverter.convertToOrder(orderRequest);

        Customer customerDB = userDB.getCustomer();
        if(customerDB!= null){
            toSaveOrder.setCustomer(customerDB);
        }

        orderRepository.save(toSaveOrder);

        return orderConverter.convertToOrderResponseDTO(toSaveOrder);
    }

    @Override
    public OrderResponseDTO handleEmployeeCreateOrder(OrderRequestDTO orderRequest) {

        User userDB = userService.getUserLogin();

        Order toSaveOrder = orderConverter.convertToOrder(orderRequest);

        Employee employeeDB = userDB.getEmployee();

        if(employeeDB!= null){
            toSaveOrder.setEmployee(employeeDB);
        }

        if(orderRequest.getCustomer()!= null && StringUtil.isValid(orderRequest.getCustomer().getId())){
            Customer customer = customerRepository.findById(orderRequest.getCustomer().getId())
                    .orElseThrow(()-> new IdInvalidException("Customer not found"));

            toSaveOrder.setCustomer(customer);
        }

        orderRepository.save(toSaveOrder);

        return orderConverter.convertToOrderResponseDTO(toSaveOrder);
    }

}
