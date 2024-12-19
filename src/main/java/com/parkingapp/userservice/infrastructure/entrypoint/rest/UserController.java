package com.parkingapp.userservice.infrastructure.entrypoint.rest;

import com.parkingapp.userservice.application.getallusers.GetAllUsersUseCase;
import com.parkingapp.userservice.application.getuserbyid.GetUserByIdUseCase;
import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.infrastructure.entrypoint.rest.response.UserDTO;
import com.parkingapp.userservice.infrastructure.entrypoint.rest.response.UsersResponse;
import com.parkingapp.userservice.infrastructure.entrypoint.rest.response.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "All about users")
public class UserController {
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;

    public UserController(
        GetAllUsersUseCase getAllUsersUseCase,
        GetUserByIdUseCase getUserByIdUseCase
    ) {
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
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
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Invalid input",
                    summary = "Example of a bad request response",
                    value = "{\"message\": \"Invalid input provided\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Server error",
                    summary = "Example of internal server error",
                    value = "{\"message\": \"Unexpected server error\"}"
                )
            )
        )
    })

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UsersResponse getUsers() {

        List<User> users = getAllUsersUseCase.execute();
        List<UserDTO> mappedUsers = users.stream()
            .map(user -> new UserDTO(user.getId(), user.getName(), user.getLastname(), user.getEmail())).toList();

        return new UsersResponse(mappedUsers);
    }

    @Operation(summary = "Get user by ID")
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
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Invalid input",
                    summary = "Example of a bad request response",
                    value = "{\"message\": \"Invalid input provided\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "User not found",
                    summary = "Example of user not found case",
                    value = "{\"message\": \"User not found\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Server error",
                    summary = "Example of internal server error",
                    value = "{\"message\": \"Unexpected server error\"}"
                )
            )
        )
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getUserById(
        @Parameter(description = "UUID of the user to be fetched", example = "406dcf63-d2da-4d09-be5f-f2c53778c33d")
        @PathVariable UUID id) {
        Optional<User> user = getUserByIdUseCase.execute(id);

        if (user.isPresent()) {
            UserDTO userDTO = new UserDTO(
                user.get().getId(),
                user.get().getName(),
                user.get().getLastname(),
                user.get().getEmail()
            );
            UsersResponse response = new UsersResponse(List.of(userDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ErrorResponse errorResponse = new ErrorResponse("User not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
