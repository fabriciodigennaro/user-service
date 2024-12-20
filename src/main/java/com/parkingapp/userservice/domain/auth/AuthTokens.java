package com.parkingapp.userservice.domain.auth;

public record AuthTokens(
    String token,
    String refreshToken
) {
}
