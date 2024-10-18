package com.parkingapp.userservice.infrastructure.entrypoint.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record UsersResponse(
    @Schema(description = "List of users") List<UserDTO> users
) {}
