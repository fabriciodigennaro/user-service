package com.parkingapp.userservice.domain.service;

public interface PasswordEncryptor {
    String encrypt(String rawPassword);
    boolean matches(String rawPassword, String encryptedPassword);
}
