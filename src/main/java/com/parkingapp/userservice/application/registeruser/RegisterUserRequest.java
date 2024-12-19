package com.parkingapp.userservice.application.registeruser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserRequest {
    private String name;
    private String lastname;
    private String email;
    private String password;
}
