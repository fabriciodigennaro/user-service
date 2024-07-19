package com.example.userservice.infrastructure.entrypoint.rest;

import com.example.userservice.domain.user.User;
import com.example.userservice.infrastructure.entrypoint.rest.response.UsersResponse;
import com.example.userservice.infrastructure.entrypoint.rest.response.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Get users data")
public class UserController {

    @Operation(summary = "List all users")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsersResponse.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    }
            )
    })

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        User user1 = new User(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", "password123");
        User user2 = new User(UUID.randomUUID(), "Jane", "Smith", "jane.smith@example.com", "password123");
        return Arrays.asList(user1, user2);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable UUID id) {
        return new User(id, "John", "Doe", "john.doe@example.com", "password123");
    }
}
