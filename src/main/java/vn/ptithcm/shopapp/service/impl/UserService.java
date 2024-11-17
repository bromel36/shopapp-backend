package vn.ptithcm.shopapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.converter.UserConverter;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Role;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;
import vn.ptithcm.shopapp.repository.UserRepository;
import vn.ptithcm.shopapp.service.IRoleService;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.PaginationUtil;

import java.util.List;
import java.util.stream.Collectors;


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
    public UserResponseDTO handleFetchUserById(String id) {
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
        user.setActive(true);
        userRepository.save(user);

        return userConverter.convertToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO handleUpdateUser(User userRequest) {
        User user = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new IdInvalidException("User with id= " + userRequest.getId() + " does not exists "));

        user.setActive(userRequest.getActive());

        userRepository.save(user);

        return this.userConverter.convertToUserResponseDTO(user);
    }

    @Override
    public PaginationResponseDTO handleGetAllUsers(Specification<User> spec, Pageable pageable) {
        Page<User> users = userRepository.findAll(spec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable,users);

        List<UserResponseDTO> userResponseDTOs = users.getContent().stream()
                        .map(it->userConverter.convertToUserResponseDTO(it))
                                .collect(Collectors.toList());

        result.setResult(userResponseDTOs);

        return result;
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
