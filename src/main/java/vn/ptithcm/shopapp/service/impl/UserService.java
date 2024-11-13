package vn.ptithcm.shopapp.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.converter.UserConverter;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Role;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;
import vn.ptithcm.shopapp.repository.UserRepository;
import vn.ptithcm.shopapp.service.IRoleService;
import vn.ptithcm.shopapp.service.IUserService;


@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;

    public UserService(UserRepository userRepository, UserConverter userConverter, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void updateUserRefreshToken(User user, String token) {
        user.setRefreshToken(token);
        userRepository.save(user);
    }

    @Override
    public User getUserByRefreshTokenAndUsername(String refreshToken, String username) {
        return this.userRepository.findByRefreshTokenAndUsername(refreshToken, username);
    }

    @Override
    public UserResponseDTO handleFetchUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("User with id= " + id + " does not exists "));

        return userConverter.convertToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO handleCreateUser(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IdInvalidException("Username " + user.getUsername() + " is exist, please try difference username");
        }

        Role role = getExistRole(user);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return userConverter.convertToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO handleUpdateUser(User userRequest) {
        return null;
    }

    public Role getExistRole(User user) {
        if (user.getRole() == null) {
            throw new IdInvalidException("Role is required");
        } else if (user.getRole().getId() == null) {
            throw new IdInvalidException("Role is required");
        }

        Role role = roleService.handleFetchRoleById(user.getRole().getId());
        return role;
    }

}
