package com.parkingapp.userservice.infrastructure.entrypoint.rest;

import com.parkingapp.userservice.application.registeruser.RegisterUserResponse;
import com.parkingapp.userservice.application.registeruser.RegisterUserUseCase;
import com.parkingapp.userservice.domain.user.Roles;
import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.common.IdGenerator;
import com.parkingapp.userservice.infrastructure.entrypoint.rest.request.RegistrationRequest;
import com.parkingapp.userservice.infrastructure.entrypoint.rest.response.UsersResponse;
import com.parkingapp.userservice.infrastructure.entrypoint.rest.response.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authorization", description = "All about authorization")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final IdGenerator idGenerator;

    public AuthController(
        RegisterUserUseCase registerUserUseCase,
        IdGenerator idGenerator
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.idGenerator = idGenerator;
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
            case RegisterUserResponse.Successful response -> ResponseEntity.status(HttpStatus.CREATED).build();
            case RegisterUserResponse.UserAlreadyExist ignored -> ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponse(String.format("Email address %s is already registered", request.email()))
            );
            case RegisterUserResponse.RegisterFailure ignored -> ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
                new ErrorResponse(String.format("Error saving user %s", request.email()))
            );
        };
    }
}
