package vn.ptithcm.shopapp.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;

@Component
public class UserConverter {

    private final ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponseDTO convertToUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);

        if(user.getRole()!= null){
            UserResponseDTO.RoleResponse roleResponse
                    = new UserResponseDTO.RoleResponse(user.getRole().getId(),user.getRole().getCode(),user.getRole().getName());
            userResponseDTO.setRole(roleResponse);
        }
        return userResponseDTO;
    }
}
