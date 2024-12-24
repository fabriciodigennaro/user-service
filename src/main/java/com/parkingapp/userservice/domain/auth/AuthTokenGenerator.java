package com.parkingapp.userservice.domain.auth;

public interface AuthTokenGenerator {
    String generateToken(String username);
    String generateRefreshToken(String username);
    boolean validateToken(String token);
    String extractUsername(String token);
}
