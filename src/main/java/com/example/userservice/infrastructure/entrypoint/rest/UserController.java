package com.example.userservice.infrastructure.entrypoint.rest;

import com.example.userservice.domain.user.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Get a user by ID")
public class UserController {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable UUID id) {
        return new User(id, "John", "Doe", "john.doe@example.com", "password123");
    }
}
