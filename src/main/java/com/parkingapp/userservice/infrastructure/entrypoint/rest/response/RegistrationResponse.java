package com.parkingapp.userservice.infrastructure.entrypoint.rest.response;

import java.util.UUID;

public record RegistrationResponse(
    UUID id,
    String name,
    String lastname,
    String email
) {}
