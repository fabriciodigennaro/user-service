package com.parkingapp.userservice.application.registeruser;

import com.parkingapp.userservice.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public abstract sealed class RegisterUserResponse {

    private RegisterUserResponse() {}

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static final class Successful extends RegisterUserResponse {
        private final User user;
    }
    public static final class UserAlreadyExist extends  RegisterUserResponse {}
    public static final class RegisterFailure extends RegisterUserResponse {}
}
