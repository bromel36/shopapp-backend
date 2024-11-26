package vn.ptithcm.shopapp.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.ChangePasswordDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;

public interface IUserService {
    User handleGetUserByUsername(String username);

    void updateUserRefreshToken(User currentUserDB, String refreshToken);

    User getUserByRefreshTokenAndUsername(String refreshToken, String username);

    UserResponseDTO handleFetchUserResponseById(Long id);

    UserResponseDTO handleCreateUser(User userRequest);

    UserResponseDTO handleUpdateUser(User userRequest);

    PaginationResponseDTO handleGetAllUsers(Specification<User> spec, Pageable pageable);

    User getUserLogin();

    UserResponseDTO handleCustomerRegister(User userRequest);

    void handleChangePassword(@Valid ChangePasswordDTO changePasswordDTO);
}
