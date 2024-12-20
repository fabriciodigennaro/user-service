package com.parkingapp.userservice.domain.auth;

import java.util.UUID;

public interface AuthTokenGenerator {
    AuthTokens generateAuthTokens(UUID userId);
}
