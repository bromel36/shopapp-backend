package vn.ptithcm.shopapp.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.LoginRequestDTO;
import vn.ptithcm.shopapp.model.response.CustomerResponseDTO;
import vn.ptithcm.shopapp.model.response.EmployeeResponseDTO;
import vn.ptithcm.shopapp.model.response.LoginResponseDTO;
import vn.ptithcm.shopapp.service.ICustomerService;
import vn.ptithcm.shopapp.service.IEmployeeService;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.SecurityUtil;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final IUserService userService;
    private final ICustomerService customerService;
    private final IEmployeeService employeeService;


    @Value("${ptithcm.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    private final String ROLE_CUSTOMER = "CUSTOMER";
    private final String ANONYMOUS = "ANONYMOUS";


    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, IUserService userService, ICustomerService customerService, IEmployeeService employeeService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/auth/login")
    @ApiMessage("success login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) {


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return handleLoginOrRefreshCase(authentication.getName());
    }


    @GetMapping("/auth/account")
    public ResponseEntity<LoginResponseDTO> getAccount() {
        String username = SecurityUtil.getCurrentUserLogin().orElse(null);

        User currentUserDB = userService.handleGetUserByUsername(username);

        LoginResponseDTO result = new LoginResponseDTO();

        if (currentUserDB != null) {
            String name = getUserLoginName(currentUserDB);
            LoginResponseDTO.UserLoginResponseDTO userLogin
                    = new LoginResponseDTO.UserLoginResponseDTO(
                    currentUserDB.getId(),
                    currentUserDB.getUsername(),
                    name,
                    currentUserDB.getRole()
            );
            result.setUser(userLogin);
        }

        return ResponseEntity.ok().body(result);
    }


    @GetMapping("/auth/refresh")
    @ApiMessage("Refresh token")
    public ResponseEntity<LoginResponseDTO> handleRefreshToken(
            @CookieValue(value = "refresh_token", defaultValue = "") String refreshToken
    ) {
        if (refreshToken.isBlank()) {
            throw new IdInvalidException("refresh token is required");
        }
        Jwt decodedJWT = this.securityUtil.checkValidRefreshToken(refreshToken);

        String email = decodedJWT.getSubject();

        User user = this.userService.getUserByRefreshTokenAndUsername(refreshToken, email);
        if (user == null) {
            throw new IdInvalidException("Refresh token is invalid!!!");
        }
        return handleLoginOrRefreshCase(email);
    }

    @PostMapping("/auth/logout")
    @ApiMessage("Logout user")
    public ResponseEntity<Void> logout() {
        String username = SecurityUtil.getCurrentUserLogin().orElse("");
        User currentUser = userService.handleGetUserByUsername(username);

        if (currentUser != null) {
            this.userService.updateUserRefreshToken(currentUser, null);

            HttpCookie deleteCookie = createCookie(null, 0);
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, deleteCookie.toString()).body(null);
        }
        throw new IdInvalidException("Logout user is invalid!!!");
    }

//    @PostMapping("/auth/register")
//    @ApiMessage("User register account")
//    public ResponseEntity<UserResponseDTO> register(@RequestBody User userRequest){
//        UserResponseDTO userCreated = userService.handleUserCreate(userRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
//    }


    public ResponseCookie createCookie(String refreshToken, long maxAge) {
        return ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .maxAge(maxAge)
                .secure(true)
                .path("/")

                .build();
    }

    public ResponseEntity<LoginResponseDTO> handleLoginOrRefreshCase(String username) {
        LoginResponseDTO responseLoginDTO = new LoginResponseDTO();
        User currentUserDB = userService.handleGetUserByUsername(username);


        if (currentUserDB != null) {
            String name = getUserLoginName(currentUserDB);

            LoginResponseDTO.UserLoginResponseDTO userLogin
                    = new LoginResponseDTO.UserLoginResponseDTO(
                    currentUserDB.getId(),
                    currentUserDB.getUsername(),
                    name,
                    currentUserDB.getRole()
            );
            responseLoginDTO.setUser(userLogin);
        }

        responseLoginDTO.setAccessToken(securityUtil.createAccessToken(username, responseLoginDTO));

        String refreshToken = this.securityUtil.createRefreshToken(username, responseLoginDTO);

        this.userService.updateUserRefreshToken(currentUserDB, refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, createCookie(refreshToken, refreshTokenExpiration).toString())
                .body(responseLoginDTO);
    }

    public String getUserLoginName(User user) {
        if (!user.getRole().getCode().toUpperCase().equals(ROLE_CUSTOMER)) {
            EmployeeResponseDTO employee = this.employeeService.handleFetchEmployeeByUserId(user.getId());
            if (employee != null) {
                return employee.getFullName();
            }
        } else {
            CustomerResponseDTO customer = this.customerService.handleFetchCustomerByUserId(user.getId());
            if (customer != null) {
                return customer.getFullName();
            }
        }
        return ANONYMOUS;
    }
}
