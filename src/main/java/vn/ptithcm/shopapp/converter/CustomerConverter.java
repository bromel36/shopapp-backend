package vn.ptithcm.shopapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.ptithcm.shopapp.model.entity.Customer;
import vn.ptithcm.shopapp.model.response.CustomerResponseDTO;

@Component
public class CustomerConverter {

    private final ModelMapper modelMapper;

    public CustomerConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public CustomerResponseDTO convertToCustomerResponseDTO(Customer customer) {
        CustomerResponseDTO responseDTO = modelMapper.map(customer, CustomerResponseDTO.class);

        CustomerResponseDTO.CustomerUser customerUser = CustomerResponseDTO.CustomerUser.builder()
                .id(customer.getUser().getId())
                .active(customer.getUser().getActive())
                .username(customer.getUser().getUsername())
                .build();

        responseDTO.setUser(customerUser);

        return responseDTO;
    }
}
