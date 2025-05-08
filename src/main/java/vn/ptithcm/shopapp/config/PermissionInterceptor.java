package vn.ptithcm.shopapp.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import vn.ptithcm.shopapp.model.entity.User;
import vn.ptithcm.shopapp.service.IPermissionService;
import vn.ptithcm.shopapp.service.IUserService;
import vn.ptithcm.shopapp.service.impl.PermissionService;
import vn.ptithcm.shopapp.util.SecurityUtil;
import vn.ptithcm.shopapp.model.entity.Role;
import vn.ptithcm.shopapp.model.entity.Permission;

import java.util.List;

@Component
@Slf4j(topic = "INTERCEPTOR")
public class PermissionInterceptor implements HandlerInterceptor {



    private final IUserService userService;

    private final IPermissionService permissionService;
    private final AntPathMatcher pathMatcher;

    public PermissionInterceptor(IUserService userService, PermissionService permissionService, AntPathMatcher matcher) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.pathMatcher = matcher;
    }

    List<String> allowedGetEndpoints = List.of(
            "/api/v1/products/**",
            "api/v1/brands/**",
            "api/v1/categories/**"
    );


    @Override
    @Transactional
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {


        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        log.info(">>> RUN preHandle");
        log.info(">>> path= " + path);
        log.info(">>> httpMethod= " + httpMethod);
        log.info(">>> requestURI= " + requestURI);


        String email = SecurityUtil.getCurrentUserLogin().orElse("");

        User user = userService.handleGetUserByUsername(email);

        if (user != null) {
            if (httpMethod.equalsIgnoreCase("GET")) {
                for (String pattern : allowedGetEndpoints) {
                    if (pathMatcher.match(pattern, path)) {
                        return true;
                    }
                }
            }
            Role role = user.getRole();
            if (role != null) {
                List<Permission> permissions = role.getPermissions();

                boolean isAllow = permissions.stream().anyMatch(
                        it -> it.getApiPath().equals(path) && it.getMethod().equals(httpMethod)
                );

                if (!isAllow) {
                    if (permissionService.isExistByApiPathAndMethod(path, httpMethod)) {
                        // xu ly truong hop goi sai api -> 404
                        throw new AccessDeniedException("Access denied");
                    }
                }
            } else {
                throw new AccessDeniedException("Access denied");
            }
        }
//        else {
//            //truong hop hi huu, co token nhung nguoi dung trong db thi khong con
//            throw new UserNoLongerException("Unauthenticated!!!!");
//        } sai roi nhung ma de lai cho biet co truong hop la co token nhung nguoi dung trong db thi khong co

        return true;
    }

}
