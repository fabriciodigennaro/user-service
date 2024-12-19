package com.parkingapp.userservice.domain.user;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private Roles role;

    public User(UUID id, String name, String lastname, String email, String password, Roles role) {
        this.id = id;
        this.name = name.toLowerCase();
        this.lastname = lastname.toLowerCase();
        this.email = email.toLowerCase();
        this.password = password;
        this.role = role;
    }

    public void setName(String name) {this.name = name.toLowerCase();}

    public void setLastname(String lastname) {this.lastname = lastname.toLowerCase();}

    public void setEmail(String email) {this.email = email.toLowerCase();}
}
