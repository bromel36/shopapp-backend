package vn.ptithcm.shopapp.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;

public interface IUserService {
    User handleGetUserByUsername(String username);

    void updateUserRefreshToken(User currentUserDB, String refreshToken);

    User getUserByRefreshTokenAndUsername(String refreshToken, String username);

    UserResponseDTO handleFetchUserById(String id);

    UserResponseDTO handleCreateUser(User userRequest);

    UserResponseDTO handleUpdateUser(User userRequest);

    PaginationResponseDTO handleGetAllUsers(Specification<User> spec, Pageable pageable);
}
