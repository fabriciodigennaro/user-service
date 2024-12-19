package com.parkingapp.userservice.infrastructure.entrypoint.rest.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RegistrationRequest(
    @NotNull(message = "Name must be valid and between 2 and 25 characters")
    @NotBlank(message = "Name must be valid and between 2 and 25 characters")
    @Length(min = 2, max = 25, message = "Name must be valid and between 2 and 25 characters")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$", message = "Name must be valid and between 2 and 25 characters")
    String name,

    @NotNull(message = "Lastname must be valid and be between 2 and 50 characters")
    @NotBlank(message = "Lastname must be valid and be between 2 and 50 characters")
    @Length(min = 2, max = 50, message = "Lastname must be valid and be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$", message = "Lastname must be valid and be between 2 and 50 characters")
    String lastname,

    @NotNull(message = "Email is required and should be valid.")
    @NotBlank(message = "Email is required and should be valid.")
    @Email(message = "Email is required and should be valid.")
    String email,

    @NotNull(message = "Password must be between 8 and 16 characters")
    @NotBlank(message = "Password must be between 8 and 16 characters")
    @Length(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    String password
) {}
