package com.example.userservice.infrastructure.entrypoint.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserDTO {
    @Schema(
            description = "User ID",
            example = "5f215120-c669-451a-97b1-57f79144548b"
    )
    private UUID id;

    @Schema(
            description = "User name",
            example = "Jon"
    )
    private String name;

    @Schema(
            description = "User lastname",
            example = "Doe"
    )
    private String lastname;

    @Schema(
            description = "User email",
            example = "jondoe@mail.com"
    )
    private String email;


    public UserDTO(UUID id, String name, String lastname, String email) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

}
