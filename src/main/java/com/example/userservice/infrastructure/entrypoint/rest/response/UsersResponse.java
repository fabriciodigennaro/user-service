package com.example.userservice.infrastructure.entrypoint.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class UsersResponse {
    @Schema(
            description = "List of users"
    )
    private List<UserDTO> users;

    public UsersResponse(List<UserDTO> users) {
        this.users = users;
    }

    public List<UserDTO> getUsers() {
        return users;
    }
}
