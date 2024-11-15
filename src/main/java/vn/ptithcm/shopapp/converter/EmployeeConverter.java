package vn.ptithcm.shopapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.ptithcm.shopapp.model.entity.Employee;
import vn.ptithcm.shopapp.model.response.EmployeeResponseDTO;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;

@Component
public class EmployeeConverter {

    private final ModelMapper modelMapper;

    public EmployeeConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public EmployeeResponseDTO convertToEmployeeResponseDTO(Employee employee) {
        EmployeeResponseDTO dto = modelMapper.map(employee, EmployeeResponseDTO.class);

        UserResponseDTO customerUser = UserResponseDTO.builder()
                .id(employee.getUser().getId())
                .active(employee.getUser().getActive())
                .username(employee.getUser().getUsername())
                .build();
        dto.setUser(customerUser);

        return dto;
    }
}
