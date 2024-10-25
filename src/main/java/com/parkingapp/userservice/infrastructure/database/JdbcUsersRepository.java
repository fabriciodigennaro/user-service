package com.parkingapp.userservice.infrastructure.database;

import com.parkingapp.userservice.domain.user.Roles;
import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.UsersRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.util.Collections.emptyMap;

public class JdbcUsersRepository implements UsersRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcUsersRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String EMAIL_PARAM = "email";

    @Override
    public List<User> getAllUsers() {
        return namedParameterJdbcTemplate.query(
            """
                   select * from users
                """,
            emptyMap(),
            new UserRowMapper()
        );
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put(EMAIL_PARAM, email);
        return namedParameterJdbcTemplate.query(
                """
                       SELECT * from users
                       WHERE email = :email
                    """,
                params,
                new UserRowMapper()
            )
            .stream().findFirst();
    }

    @Override
    public boolean save(User user) {
        String sql =
            """
                INSERT INTO users (id, name, lastname, email, password, role, updated)
                VALUES (:id, :name, :lastname, :email, :password, :role, CURRENT_TIMESTAMP)
                ON CONFLICT(email) DO NOTHING;
            """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        params.put("name", user.getName());
        params.put("lastname", user.getLastname());
        params.put(EMAIL_PARAM, user.getEmail());
        params.put("password", user.getPassword());
        params.put("role", user.getRole().name());

        int rowsAffected = namedParameterJdbcTemplate.update(sql, params);

        return rowsAffected > 0;
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("lastname"),
                rs.getString(EMAIL_PARAM),
                rs.getString("password"),
                Roles.valueOf(rs.getString("role"))
            );
        }
    }
}
