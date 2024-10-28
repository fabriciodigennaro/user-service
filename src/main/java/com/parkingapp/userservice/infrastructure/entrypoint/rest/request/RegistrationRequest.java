package com.parkingapp.userservice.infrastructure.entrypoint.rest.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrationRequest(
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    String name,

    @NotNull(message = "Lastname is required")
    @NotBlank(message = "Lastname is required")
    String lastname,

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email,

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    String password
) {}
