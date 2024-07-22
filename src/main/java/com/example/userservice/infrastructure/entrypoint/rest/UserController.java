package com.example.userservice.infrastructure.entrypoint.rest;

import com.example.userservice.application.getAllUsers.GetAllUsersUseCase;
import com.example.userservice.domain.user.User;
import com.example.userservice.infrastructure.entrypoint.rest.response.UserDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Get users data")
public class UserController {
    private final GetAllUsersUseCase getAllUsersUseCase;

    public UserController(GetAllUsersUseCase getAllUsersUseCase) {
        this.getAllUsersUseCase = getAllUsersUseCase;
    }

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
    public UsersResponse getUsers() {

        List<User> users = getAllUsersUseCase.execute();
        List<UserDTO> mappedUsers = users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getLastname(), user.getEmail()))
                .collect(Collectors.toList());

        return new UsersResponse(mappedUsers);
    }

    //TODO replace mock with real data from DB
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable UUID id) {
        return new User(id, "John", "Doe", "john.doe@example.com", "password123");
    }
}
