package vn.ptithcm.shopapp.service;

import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;

public interface IUserService {
    User handleGetUserByUsername(String username);

    void updateUserRefreshToken(User currentUserDB, String refreshToken);

    User getUserByRefreshTokenAndUsername(String refreshToken, String username);

    UserResponseDTO handleFetchUserById(Long id);

    UserResponseDTO handleCreateUser(User userRequest);

    UserResponseDTO handleUpdateUser(User userRequest);
}
