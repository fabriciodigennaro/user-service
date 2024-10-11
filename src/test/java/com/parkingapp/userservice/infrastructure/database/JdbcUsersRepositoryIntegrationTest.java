package com.parkingapp.userservice.infrastructure.database;

import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.UsersRepository;
import com.parkingapp.userservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import com.parkingapp.userservice.infrastructure.fixtures.initializers.testannotation.WithPostgreSql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@WithPostgreSql
public class JdbcUsersRepositoryIntegrationTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    public void setUp() {
        JdbcTestUtils.deleteFromTables(
                namedParameterJdbcTemplate.getJdbcTemplate(),
                "users"
        );
    }

    @Test
    public void shouldReturnAllUsers() {
        // GIVEN
        User user1 = new User(UUID.randomUUID(), "name1", "lastname1", "user1@mail.com", "1234abc");
        User user2 = new User(UUID.randomUUID(), "name2", "lastname2", "user2@mail.com", "1234abc");
        List<User> expectedUsers = List.of(user1, user2);

        givenExistingUser(user1);
        givenExistingUser(user2);

        // WHEN
        List<User> result = usersRepository.getAllUsers();

        // THEN
        assertThat(result).isEqualTo(expectedUsers);
    }

    @Test
    void shouldReturnAUserByUserId() {
        // GIVEN
        UUID userId = UUID.randomUUID();
        User user1 = new User(userId, "name1", "lastname1", "user1@mail.com", "1234abc");
        User user2 = new User(UUID.randomUUID(), "name2", "lastname2", "user2@mail.com", "1234abc");

        givenExistingUser(user1);
        givenExistingUser(user2);

        // WHEN
        Optional<User> result = usersRepository.getUserById(userId);

        // THEN
        assertThat(result).isPresent();
        assertThat(result).isEqualTo(Optional.of(user1));
    }

    @Test
    void shouldReturnAEmptyOptionalWhenUserIdNotFound() {
        // GIVEN
        UUID expectedUserId = UUID.randomUUID();
        User user = new User(UUID.randomUUID(), "name2", "lastname2", "user2@mail.com", "1234abc");

        givenExistingUser(user);

        // WHEN
        Optional<User> result = usersRepository.getUserById(expectedUserId);

        // THEN
        assertThat(result).isEmpty();
    }

    private void givenExistingUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("lastname", user.getLastname())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword());

        namedParameterJdbcTemplate.update(
                "INSERT INTO users(id, name, lastname, email, password) VALUES (:id, :name, :lastname, :email, :password);",
                params
        );
    }

}