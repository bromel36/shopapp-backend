package vn.ptithcm.shopapp.service;

import vn.ptithcm.shopapp.model.entity.User;

public interface IUserService {
    User handleGetUserByUsername(String username);

    void updateUserRefreshToken(User currentUserDB, String refreshToken);

    User getUserByRefreshTokenAndUsername(String refreshToken, String username);
}
