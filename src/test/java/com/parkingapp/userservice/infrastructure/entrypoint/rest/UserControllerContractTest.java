package com.parkingapp.userservice.infrastructure.entrypoint.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.userservice.application.getallusers.GetAllUsersUseCase;
import com.parkingapp.userservice.application.getuserbyemail.GetUserByEmailUseCase;
import com.parkingapp.userservice.application.registeruser.RegisterUserUseCase;
import com.parkingapp.userservice.domain.user.Roles;
import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.infrastructure.entrypoint.rest.response.UserDTO;
import com.parkingapp.userservice.infrastructure.entrypoint.rest.response.UsersResponse;
import com.parkingapp.userservice.infrastructure.fixtures.initializers.testannotation.ContractTest;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContractTest
@WebMvcTest(controllers = UserController.class)
class UserControllerContractTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetAllUsersUseCase getAllUsersUseCase;

    @MockBean
    private GetUserByEmailUseCase getUserByEmailUseCase;

    @MockBean
    private RegisterUserUseCase registerUserUseCase;

    UUID userId = UUID.randomUUID();
    String name = "john";
    String lastname = "doe";
    String email = "jon@mail.com";
    User user1 = new User(userId, name, lastname, email, "123", Roles.USER);

    @Nested
    class GetAllUsers {
        @Test
        void shouldGetAllUsers() throws JsonProcessingException {
            // GIVEN
            User user2 = new User(
                UUID.randomUUID(),
                "max",
                "steel",
                "max@mail.com",
                "12",
                Roles.USER
            );
            when(getAllUsersUseCase.execute()).thenReturn(List.of(user1, user2));
            UserDTO userDto1 = new UserDTO(user1.getId(), user1.getName(), user1.getLastname(), user1.getEmail());
            UserDTO userDto2 = new UserDTO(user2.getId(), user2.getName(), user2.getLastname(), user2.getEmail());

            String expectedResponse = objectMapper.writeValueAsString(
              new UsersResponse(
                      List.of(userDto1,userDto2)
              )
            );

            // WHEN
            MockMvcResponse response = whenARequestToGetAllUsersIsReceived();

            // THEN
            response.then()
                    .statusCode(HttpStatus.OK.value())
                    .body(CoreMatchers.equalTo(expectedResponse));

            verify(getAllUsersUseCase).execute();
        }

        @Test
        void shouldReturn500WhenErrorOccurs() {
            // GIVEN
            when(getAllUsersUseCase.execute()).thenThrow(new RuntimeException("ops"));

            // WHEN
            MockMvcResponse response = whenARequestToGetAllUsersIsReceived();

            // THEN
            response.then()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            verify(getAllUsersUseCase).execute();
        }
    }

    @Nested
    class GetAUserById {
        @Test
        void shouldGetAUserByEmail() throws JsonProcessingException {
            // GIVEN
            when(getUserByEmailUseCase.execute(user1.getEmail())).thenReturn(Optional.of(user1));
            UserDTO userDto1 = new UserDTO(user1.getId(), user1.getName(), user1.getLastname(), user1.getEmail());

            String expectedResponse = objectMapper.writeValueAsString(
                    new UsersResponse(
                            List.of(userDto1)
                    )
            );

            // WHEN
            MockMvcResponse response = whenARequestToGetAUserByIdIsReceived(user1.getEmail());

            // THEN
            response.then()
                    .statusCode(HttpStatus.OK.value())
                    .body(CoreMatchers.equalTo(expectedResponse));

            verify(getUserByEmailUseCase).execute(user1.getEmail());
        }

        @Test
        void shouldReturn404WhenUserNotFound() {
            // GIVEN
            when(getUserByEmailUseCase.execute(user1.getEmail())).thenReturn(Optional.empty());

            // WHEN
            MockMvcResponse response = whenARequestToGetAUserByIdIsReceived(user1.getEmail());

            // THEN
            response.then()
                    .statusCode(HttpStatus.NOT_FOUND.value());

            verify(getUserByEmailUseCase).execute(user1.getEmail());
        }

        @Test
        void shouldReturn500WhenErrorOccurs() {
            // GIVEN
            when(getUserByEmailUseCase.execute(email)).thenThrow(new RuntimeException("ops"));

            // WHEN
            MockMvcResponse response = whenARequestToGetAUserByIdIsReceived(email);

            // THEN
            response.then()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            verify(getUserByEmailUseCase).execute(email);
        }
    }

    private MockMvcResponse whenARequestToGetAllUsersIsReceived() {
        return RestAssuredMockMvc
                .given()
                .webAppContextSetup(context)
                .contentType(ContentType.JSON)
                .when()
                .get("/users");
    }

    private MockMvcResponse whenARequestToGetAUserByIdIsReceived(String email) {
        return RestAssuredMockMvc
                .given()
                .webAppContextSetup(context)
                .contentType(ContentType.JSON)
                .pathParam("email", email)
                .when()
                .get("/users/{email}");
    }

}