package com.parkingapp.userservice.infrastructure.entrypoint.rest.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrationRequest(
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    String name,

    @NotNull(message = "Lastname is required")
    @NotBlank(message = "Lastname cannot be blank")
    String lastname,

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    String email,

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password cannot be blank")
    String password
) {}
