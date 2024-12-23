package com.parkingapp.userservice.infrastructure.config;

import com.parkingapp.userservice.domain.auth.AuthTokenGenerator;
import com.parkingapp.userservice.infrastructure.auth.JwtAuthTokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {
    @Bean
    public AuthTokenGenerator authTokenGenerator() {
        return new JwtAuthTokenGenerator();
    }
}
