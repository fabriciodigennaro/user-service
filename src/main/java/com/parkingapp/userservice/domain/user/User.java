package com.parkingapp.userservice.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String lastname;
    private String email;
    private String password;
}
