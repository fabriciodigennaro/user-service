package com.parkingapp.userservice.infrastructure.service;

import com.parkingapp.userservice.domain.service.PasswordEncryptor;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptPasswordEncryptor implements PasswordEncryptor {
    @Override
    public String encrypt(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
}
