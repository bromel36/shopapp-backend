package vn.ptithcm.shopapp.util;

import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import vn.ptithcm.shopapp.error.IdInvalidException;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.model.response.LoginResponseDTO;
import vn.ptithcm.shopapp.service.IUserService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@Service
public class SecurityUtil {

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
    public static final String ROLE_CUSTOMER = "CUSTOMER";
    public static final String INITIAL_PASSWORD = "123456";
    public static final String ROLE_ADMIN = "SYSADMIN";

    private final JwtEncoder jwtEncoder;

    public static final String[] whiteList = {
            "/", "/api/v1/auth/refresh", "/api/v1/auth/register", "/api/v1/auth/login", "/api/v1/auth/verify-email",
            "/api/v1/auth/resend-verify-email", "/api/v1/auth/reset-pwd/**",
            "/storage/**",
            "/default-avatar.jpg",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api-document.html",
            "/send/**",
            "/api/v1/users/forgot-pwd/**",
            "/api/v1/chat/**"
    };

    public static final String[] getWhiteList = {
            "/api/v1/products/**",
            "api/v1/brands/**",
            "api/v1/categories/**"
    };

    public SecurityUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Value("${ptithcm.jwt.base64-secret}")
    private String jwtKey;

    public String createToken(String email, LoginResponseDTO loginResponseDTO, long expirationTime) {
        Instant now = Instant.now();
        Instant validity = now.plus(expirationTime, ChronoUnit.SECONDS);
        // @formatter:off
        LoginResponseDTO.UserInsideToken userInsideToken = new LoginResponseDTO.UserInsideToken();
        userInsideToken.setId(loginResponseDTO.getUser().getId());
        userInsideToken.setEmail(loginResponseDTO.getUser().getEmail());
        userInsideToken.setFullName(loginResponseDTO.getUser().getFullName());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("user", userInsideToken)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
    public String createVerifyToken(String email, long expirationTime){
        Instant now = Instant.now();
        Instant validity = now.plus(expirationTime, ChronoUnit.SECONDS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }


    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }

    public Jwt checkValidToken(String token) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(
                getSecretKey()).macAlgorithm(SecurityUtil.JWT_ALGORITHM).build();

        return jwtDecoder.decode(token);
    }
    public String extractEmailFromToken(String token) {
        Jwt decodedJwt = checkValidToken(token);
        return decodedJwt.getSubject();
    }

    public boolean isTokenExpired(String token) {
        Jwt decodedJwt = checkValidToken(token);
        Instant now = Instant.now();
        return Objects.requireNonNull(decodedJwt.getExpiresAt()).isBefore(now);
    }

    public void checkCustomerIdAccess(long id, User currentUserLogin){


        if(id != currentUserLogin.getId() && SecurityUtil.isCustomer(currentUserLogin)){
            throw new IdInvalidException("Access denied!!!");
        }
    }

    public static boolean isCustomer(User user){
        return user.getRole().getCode().equalsIgnoreCase(ROLE_CUSTOMER);
    }


    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getCredentials() instanceof String)
                .map(authentication -> (String) authentication.getCredentials());
    }


}
