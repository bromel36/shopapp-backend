package vn.ptithcm.shopapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMaperConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
