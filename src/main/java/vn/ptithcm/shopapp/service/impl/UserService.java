package vn.ptithcm.shopapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.converter.UserConverter;
import vn.ptithcm.shopapp.enums.TokenTypeEnum;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.Role;
import vn.ptithcm.shopapp.model.entity.Token;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.ChangePasswordDTO;
import vn.ptithcm.shopapp.model.request.ForgotPasswordDTO;
import vn.ptithcm.shopapp.model.request.ResendVerifyEmailRequestDTO;
import vn.ptithcm.shopapp.model.response.PaginationResponseDTO;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;
import vn.ptithcm.shopapp.repository.TokenRepository;
import vn.ptithcm.shopapp.repository.UserRepository;
import vn.ptithcm.shopapp.service.IEmailService;
import vn.ptithcm.shopapp.service.IRoleService;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.PaginationUtil;
import vn.ptithcm.shopapp.util.SecurityUtil;
import vn.ptithcm.shopapp.util.StringUtil;

import java.util.List;
import java.util.Objects;


@Service
@Slf4j(topic = "USER-SERVICE")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {


    String defaultAvatar;

    long expirationTime;

    UserRepository userRepository;
    UserConverter userConverter;
    PasswordEncoder passwordEncoder;
    IRoleService roleService;
    IEmailService emailService;
    SecurityUtil securityUtil;
    TokenRepository tokenRepository;

    public UserService(@Value("${ptithcm.avatar.default}") String defaultAvatar,@Value("${ptithcm.jwt.verify-token-validity-in-seconds}") long expirationTime, UserRepository userRepository, UserConverter userConverter, PasswordEncoder passwordEncoder, IRoleService roleService, IEmailService emailService, SecurityUtil securityUtil, TokenRepository tokenRepository) {
        this.defaultAvatar = defaultAvatar;
        this.expirationTime = expirationTime;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.emailService = emailService;
        this.securityUtil = securityUtil;
        this.tokenRepository = tokenRepository;
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
    public UserResponseDTO handleFetchUserResponseById(Long id) {
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

        if (!StringUtil.isValid(user.getAvatar())) {
            user.setAvatar(defaultAvatar);
        }
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
        user.setAvatar(StringUtil.isValid(userRequest.getAvatar()) ? userRequest.getAvatar() : defaultAvatar);

        userRepository.save(user);

        return this.userConverter.convertToUserResponseDTO(user);
    }

    @Override
    public PaginationResponseDTO handleGetAllUsers(Specification<User> spec, Pageable pageable) {
        Page<User> users = userRepository.findAll(spec, pageable);

        PaginationResponseDTO result = PaginationUtil.handlePaginate(pageable, users);

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

    public User getUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("User with id= " + id + " does not exists "));

        return user;
    }

    @Transactional
    @Override
    public UserResponseDTO handleCustomerRegister(User userRequest, String clientType) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new IdInvalidException("Username " + userRequest.getEmail() + " is exist, please try difference username");
        }

        Role customerRole = roleService.handldeFetchRoleByCode(SecurityUtil.ROLE_CUSTOMER);
        if (customerRole != null) {
            userRequest.setRole(customerRole);
        }

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRequest.setActive(false);

        if (!StringUtil.isValid(userRequest.getAvatar())) {
            userRequest.setAvatar(defaultAvatar);
        }
        String verifyToken = securityUtil.createVerifyToken(userRequest.getEmail(), expirationTime);

        Token token = new Token();
        token.setToken(verifyToken);
        token.setType(TokenTypeEnum.VERIFIED);
        token.setUser(userRequest);

        tokenRepository.save(token);

        userRepository.save(userRequest);

        emailService.sendVerifyMail(userRequest,token,clientType);
        return userConverter.convertToUserResponseDTO(userRequest);
    }


    @Override
    public void handleChangePassword(ChangePasswordDTO changePasswordDTO) {
        User user = getUserLogin();

        if (changePasswordDTO.getOldPassword().equals(changePasswordDTO.getNewPassword())){
            throw new IdInvalidException("New password must different from old password");
        }

        boolean isMatched = passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword());


        if(!isMatched){
            throw new IdInvalidException("Your old password is incorrect!!!");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));

        userRepository.save(user);
    }

    @Override
    public void handleForgotPassword(ForgotPasswordDTO forgotPasswordDTO, String clientType) {
        String email = forgotPasswordDTO.getEmail();
        User userDB = userRepository.findByEmail(email);

        if(userDB == null){
            throw new IdInvalidException("User not found");
        }

        String tokenStr = securityUtil.createVerifyToken(email, expirationTime);

        Token token = new Token();
        token.setType(TokenTypeEnum.RESET_PASSWORD);
        token.setToken(tokenStr);
        token.setUser(userDB);

        emailService.sendVerifyMail(userDB, token, clientType);

        tokenRepository.save(token);
    }

    @Transactional
    @Override
    public Object handleVerifyUser(String token, TokenTypeEnum type) {
        if(securityUtil.isTokenExpired(token)){
            throw new IdInvalidException("Expired token");
        }
        String userEmail = securityUtil.extractEmailFromToken(token);

        User userDB = userRepository.findByEmail(userEmail);

        if(userDB == null){
            throw new IdInvalidException("User may not exist");
        }

        List<Token> tokens = userDB.getTokens();

        Token contextToken = tokens.stream()
                .filter(it -> it.getType() == type)
                .findFirst()
                .orElseThrow(() -> new IdInvalidException("Token of this type not found"));


        if (!Objects.equals(contextToken.getToken(), token)) {
            throw new IdInvalidException("Token invalid");
        }

        if(type == TokenTypeEnum.VERIFIED){
            userDB.setActive(true);
        }
        else if (type == TokenTypeEnum.RESET_PASSWORD){

        }

        tokenRepository.delete(contextToken);
        userRepository.save(userDB);

        log.info("User verified");
        return "User verified successfully";
    }

    @Transactional
    @Override
    public void resendEmail(ResendVerifyEmailRequestDTO dto, String clientType) {
        User userDB = userRepository.findByEmail(dto.getEmail());

        if(userDB == null){
            throw new IdInvalidException("User not found");
        }

        tokenRepository.deleteByUserIdAndType(userDB.getId(), dto.getType());

        String resendToken = securityUtil.createVerifyToken(userDB.getEmail(), expirationTime);

        Token token = new Token();
        token.setToken(resendToken);
        token.setType(dto.getType());
        token.setUser(userDB);

        emailService.sendVerifyMail(userDB, token, clientType);

        tokenRepository.save(token);
        log.info("Saved token");
    }


    public Role getExistRole(Role role) {
        if (role == null || role.getId() == null) {
            throw new IdInvalidException("Role is required");
        }

        Role roleDB = roleService.handleFetchRoleById(role.getId());
        return roleDB;
    }


}
