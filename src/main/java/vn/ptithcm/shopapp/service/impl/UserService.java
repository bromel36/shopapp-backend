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
import vn.ptithcm.shopapp.util.SecurityUtil;

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
        return this.userRepository.findByEmail(username);
    }

    @Override
    public void updateUserRefreshToken(User user, String token) {
        user.setRefreshToken(token);
        userRepository.save(user);
    }

    @Override
    public User getUserByRefreshTokenAndUsername(String refreshToken, String username) {
        return this.userRepository.findByRefreshTokenAndEmail(refreshToken, username);
    }

    @Override
    public UserResponseDTO handleFetchUserById(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("User with id= " + id + " does not exists "));

        return userConverter.convertToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO handleCreateUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IdInvalidException("Username " + user.getEmail() + " is exist, please try difference username");
        }

        Role role = getExistRole(user.getRole());

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
        user.setFullName(userRequest.getFullName());
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
        user.setGender(userRequest.getGender());
        user.setBirthday(userRequest.getBirthday());
        user.setShoppingAddress(userRequest.getShoppingAddress());
        user.setAvatar(userRequest.getAvatar());

        userRepository.save(user);

        return this.userConverter.convertToUserResponseDTO(user);
    }

    @Override
    public PaginationResponseDTO handleGetAllUsers(Specification<User> spec, Pageable pageable) {
        Page<User> users = userRepository.findAll(spec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable,users);

        List<UserResponseDTO> userResponseDTOs = users.getContent().stream()
                        .map(userConverter::convertToUserResponseDTO)
                        .toList();


        result.setResult(userResponseDTOs);

        return result;
    }

    @Override
    public User getUserLogin() {
        String username = SecurityUtil.getCurrentUserLogin()
                .orElseThrow(() -> new IdInvalidException("User not logged in"));


        return handleGetUserByUsername(username);
    }

    @Override
    public UserResponseDTO handleCustomerRegister(User userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new IdInvalidException("Username " + userRequest.getEmail() + " is exist, please try difference username");
        }

        Role customerRole = roleService.handldeFetchRoleByCode(SecurityUtil.ROLE_CUSTOMER);
        if(customerRole!= null){
            userRequest.setRole(customerRole);
        }

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRequest.setActive(true);

        userRepository.save(userRequest);

        return userConverter.convertToUserResponseDTO(userRequest);
    }


    public Role getExistRole(Role role) {
        if (role == null || role.getId() == null) {
            throw new IdInvalidException("Role is required");
        }

        Role roleDB = roleService.handleFetchRoleById(role.getId());
        return roleDB;
    }


}
