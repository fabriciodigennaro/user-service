package com.example.userservice.infrastructure.entrypoint.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class UserDTO {
    @Getter
    @Setter
    @Schema(
            description = "User ID",
            example = "5f215120-c669-451a-97b1-57f79144548b"
    )
    private UUID id;

    @Getter
    @Setter
    @Schema(
            description = "User name",
            example = "Jon"
    )
    private String name;

    @Getter
    @Setter
    @Schema(
            description = "User lastname",
            example = "Doe"
    )
    private String lastname;

    @Getter
    @Setter
    @Schema(
            description = "User email",
            example = "jondoe@mail.com"
    )
    private String email;

    public UserDTO() {
    }

    public UserDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

}
