package vn.ptithcm.shopapp.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.request.LoginRequestDTO;
import vn.ptithcm.shopapp.model.response.LoginResponseDTO;
import vn.ptithcm.shopapp.model.response.UserResponseDTO;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.util.SecurityUtil;
import vn.ptithcm.shopapp.util.annotations.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final IUserService userService;


    @Value("${ptithcm.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;


    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, IUserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
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

            LoginResponseDTO.UserLoginResponseDTO userLogin
                    = new LoginResponseDTO.UserLoginResponseDTO(
                    currentUserDB.getId(),
                    currentUserDB.getEmail(),
                    currentUserDB.getFullName(),
                    currentUserDB.getAvatar(),
                    currentUserDB.getPhone(),
                    new LoginResponseDTO.RoleLoginResponse(currentUserDB.getRole().getCode())
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

        User currentUser = userService.getUserLogin();

        if (currentUser != null) {
            this.userService.updateUserRefreshToken(currentUser, null);

            HttpCookie deleteCookie = createCookie(null, 0);
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, deleteCookie.toString()).body(null);
        }
        throw new IdInvalidException("Logout user is invalid!!!");
    }

    @PostMapping("/auth/register")
    @ApiMessage("User register account")
    public ResponseEntity<UserResponseDTO> register(@RequestBody User userRequest){
        UserResponseDTO customerRegister = userService.handleCustomerRegister(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerRegister);
    }


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

            LoginResponseDTO.UserLoginResponseDTO userLogin
                    = new LoginResponseDTO.UserLoginResponseDTO(
                    currentUserDB.getId(),
                    currentUserDB.getEmail(),
                    currentUserDB.getFullName(),
                    currentUserDB.getAvatar(),
                    currentUserDB.getPhone(),
                    new LoginResponseDTO.RoleLoginResponse(currentUserDB.getRole().getCode())
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
}
