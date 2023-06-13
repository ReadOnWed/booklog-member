package com.booklog.config;

import com.booklog.jwt.service.JwtService;
import com.booklog.jwt.service.JwtServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public JwtService jwtService() {
        return new JwtServiceImpl();
    }
}
