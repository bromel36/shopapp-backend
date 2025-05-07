package vn.ptithcm.shopapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PermissionInterceptorConfig implements WebMvcConfigurer {

    private final PermissionInterceptor permissionInterceptor;

    public PermissionInterceptorConfig(PermissionInterceptor permissionInterceptor) {
        this.permissionInterceptor = permissionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] whiteList = {
                "/", "/api/v1/auth/**", "/storage/**", "/api/v1/files",
                "/api/v1/subscribers/**"
        };
        // list nay la sau khi access token da duoc thong qua roi, thi bat cu nguoi dung nao
        // cung co the su dung
        registry.addInterceptor(permissionInterceptor)
                .excludePathPatterns(whiteList);
    }
}
