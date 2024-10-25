package com.parkingapp.userservice.infrastructure.entrypoint.rest.request;

public record RegistrationRequest(
    String name,
    String lastname,
    String email,
    String password
) {}
