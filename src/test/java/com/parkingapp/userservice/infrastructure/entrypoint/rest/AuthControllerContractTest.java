package com.parkingapp.userservice.infrastructure.entrypoint.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.userservice.application.registeruser.RegisterUserResponse;
import com.parkingapp.userservice.application.registeruser.RegisterUserUseCase;
import com.parkingapp.userservice.domain.user.Roles;
import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.common.IdGenerator;
import com.parkingapp.userservice.infrastructure.fixtures.initializers.testannotation.ContractTest;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContractTest
@WebMvcTest(controllers = AuthController.class)
class AuthControllerContractTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterUserUseCase registerUserUseCase;

    @MockBean
    private IdGenerator idGenerator;

    UUID userId = UUID.randomUUID();
    String name = "john";
    String lastname = "doe";
    String email = "jon@mail.com";
    String password = "abcd12345";
    User user1 = new User(userId, name, lastname, email, password, Roles.USER);

    String requestBody = String.format(
        """
            {
              "name": "%s",
              "lastname": "%s",
              "email": "%s",
              "password": "%s"
            }
        """,
        name,
        lastname,
        email,
        password
    );

    @Nested
    class RegisterANewUser {

        @Test
        void shouldRegisterANewUser() {
            // GIVEN
            when(idGenerator.generate()).thenReturn(userId);
            when(registerUserUseCase.execute(user1)).thenReturn(new RegisterUserResponse.Successful(user1));

            // WHEN
            MockMvcResponse response = whenARequestToRegisterNewUserIsReceived(requestBody);

            // THEN
            response.then()
                .statusCode(HttpStatus.CREATED.value());
            verify(registerUserUseCase).execute(user1);
        }

        @ParameterizedTest
        @CsvSource({
            "name, Name must be valid and between 2 and 25 characters",
            "lastname, Lastname must be valid and be between 2 and 50 characters",
            "email, Email is required and should be valid.",
            "password, Password must be between 8 and 16 characters"
        })
        void shouldReturnError400WhenFieldIsNull(String field, String expectedMessage) throws JsonProcessingException {
            // GIVEN
            Map<String, Object> userFields = new HashMap<>();
            userFields.put("name", name);
            userFields.put("lastname", lastname);
            userFields.put("email", email);
            userFields.put("password", password);
            userFields.put(field, null);

            String requestBodyWithNulls = new ObjectMapper().writeValueAsString(userFields);

            // WHEN
            MockMvcResponse response = whenARequestToRegisterNewUserIsReceived(requestBodyWithNulls);

            // THEN
            response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(field, equalTo(expectedMessage));
        }

        @ParameterizedTest
        @CsvSource({
            "name, Name must be valid and between 2 and 25 characters",
            "lastname, Lastname must be valid and be between 2 and 50 characters",
            "email, Email is required and should be valid.",
            "password, Password must be between 8 and 16 characters"
        })
        void shouldReturnError400WhenFieldIsBlank(String field, String expectedMessage) throws JsonProcessingException {
            // GIVEN
            Map<String, Object> userFields = new HashMap<>();
            userFields.put("name", name);
            userFields.put("lastname", lastname);
            userFields.put("email", email);
            userFields.put("password", password);
            userFields.put(field, "");

            String requestBodyWithBlanks = new ObjectMapper().writeValueAsString(userFields);

            // WHEN
            MockMvcResponse response = whenARequestToRegisterNewUserIsReceived(requestBodyWithBlanks);

            // THEN
            response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(field, equalTo(expectedMessage));
        }

        @ParameterizedTest
        @CsvSource({
            "incorrect.mail, Email is required and should be valid.",
            "invalid@.com, Email is required and should be valid."
        })
        void shouldReturnError400WhenEmailIsInvalid(String email, String expectedMessage) throws JsonProcessingException {
            // GIVEN
            Map<String, Object> userFields = new HashMap<>();
            userFields.put("name", name);
            userFields.put("lastname", lastname);
            userFields.put("email", email);
            userFields.put("password", password);

            String requestBodyWithInvalidEmail = new ObjectMapper().writeValueAsString(userFields);

            // WHEN
            MockMvcResponse response = whenARequestToRegisterNewUserIsReceived(requestBodyWithInvalidEmail);

            // THEN
            response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("email", equalTo(expectedMessage));
        }

        @Test
        void shouldReturnError409WhenUserAlreadyExists() {
            // GIVEN
            when(idGenerator.generate()).thenReturn(userId);
            when(registerUserUseCase.execute(user1)).thenReturn(new RegisterUserResponse.UserAlreadyExist());

            // WHEN
            MockMvcResponse response = whenARequestToRegisterNewUserIsReceived(requestBody);

            // THEN
            response.then()
                .statusCode(HttpStatus.CONFLICT.value());
            verify(registerUserUseCase).execute(user1);
        }

        @Test
        void shouldReturn500WhenErrorOccurs() {
            // GIVEN
            when(registerUserUseCase.execute(user1)).thenThrow(new RuntimeException("ops"));

            // WHEN
            MockMvcResponse response = whenARequestToRegisterNewUserIsReceived(requestBody);

            // THEN
            response.then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private MockMvcResponse whenARequestToRegisterNewUserIsReceived(String requestBody) {
        return given()
            .webAppContextSetup(context)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/api/v1/auth/registration");
    }

}