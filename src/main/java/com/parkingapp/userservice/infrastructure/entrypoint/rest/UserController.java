package com.parkingapp.userservice.infrastructure.entrypoint.rest;

import com.parkingapp.userservice.application.getallusers.GetAllUsersUseCase;
import com.parkingapp.userservice.application.getuserbyemail.GetUserByEmailUseCase;
import com.parkingapp.userservice.application.registeruser.RegisterUserResponse;
import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.RegisterFailure;
import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.Successful;
import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.UserAlreadyExist;
import com.parkingapp.userservice.application.registeruser.RegisterUserUseCase;
import com.parkingapp.userservice.domain.user.Roles;
import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.common.IdGenerator;
import com.parkingapp.userservice.infrastructure.entrypoint.rest.request.RegistrationRequest;
import com.parkingapp.userservice.infrastructure.entrypoint.rest.response.RegistrationResponse;
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
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "All about users")
public class UserController {
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final IdGenerator idGenerator;

    public UserController(
        GetAllUsersUseCase getAllUsersUseCase,
        GetUserByEmailUseCase getUserByEmailUseCase,
        RegisterUserUseCase registerUserUseCase,
        IdGenerator idGenerator
    ) {
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getUserByEmailUseCase = getUserByEmailUseCase;
        this.registerUserUseCase = registerUserUseCase;
        this.idGenerator = idGenerator;
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
                    value = "{\"message\": \"User example@mail.com not found\"}"
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
    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getUserByEmail(
        @Parameter(description = "UUID of the user to be fetched", example = "406dcf63-d2da-4d09-be5f-f2c53778c33d")
        @PathVariable String email) {
        Optional<User> user = getUserByEmailUseCase.execute(email);

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

    @Operation(summary = "Register a user")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
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
            responseCode = "409",
            description = "Conflict saving User",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "User already exists",
                    summary = "Example of conflict response when user already exists",
                    value = "{\"message\": \"Email address example@mail.com is already registered\"}"
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
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> register(@RequestBody @Valid RegistrationRequest request) {
        User userToSave = new User(
            idGenerator.generate(),
            request.name(),
            request.lastname(),
            request.email(),
            request.password(),
            Roles.USER
        );
        RegisterUserResponse newUser = registerUserUseCase.execute(userToSave);
        return switch (newUser) {
            case Successful response -> ResponseEntity.status(HttpStatus.CREATED).body(
                new RegistrationResponse(
                    userToSave.getId(),
                    response.getUser().getName(),
                    response.getUser().getLastname(),
                    response.getUser().getEmail()
                )
            );
            case UserAlreadyExist ignored -> ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponse(String.format("Email address %s is already registered", request.email()))
            );
            case RegisterFailure ignored -> ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
                new ErrorResponse(String.format("Error saving user %s", request.email()))
            );
        };
    }
}
