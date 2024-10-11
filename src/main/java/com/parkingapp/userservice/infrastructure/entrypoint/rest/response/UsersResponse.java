package com.parkingapp.userservice.infrastructure.entrypoint.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class UsersResponse {
    @Schema(
            description = "List of users"
    )
    private final List<UserDTO> users;

    public UsersResponse(List<UserDTO> users) {
        this.users = users;
    }

    public List<UserDTO> getUsers() { return users; }
}
