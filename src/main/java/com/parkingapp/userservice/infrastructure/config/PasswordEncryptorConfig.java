package com.parkingapp.userservice.infrastructure.config;

import com.parkingapp.userservice.domain.service.PasswordEncryptor;
import com.parkingapp.userservice.infrastructure.service.BCryptPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordEncryptorConfig {
    @Bean
    public PasswordEncryptor passwordEncryptor() {
        return new BCryptPasswordEncryptor();
    }
}
