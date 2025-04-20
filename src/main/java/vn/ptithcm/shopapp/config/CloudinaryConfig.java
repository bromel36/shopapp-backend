package vn.ptithcm.shopapp.config;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${spring.cloudinary.name}")
    private String cloudName;

    @Value("${spring.cloudinary.api.key}")
    private String cloudApiKey;

    @Value("${spring.cloudinary.api.secret}")
    private String cloudApiSecret;


    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", cloudApiKey,
                "api_secret", cloudApiSecret,
                "secure", true
        ));
    }
}
