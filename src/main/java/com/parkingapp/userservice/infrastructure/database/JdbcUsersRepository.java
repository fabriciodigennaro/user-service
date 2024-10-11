package com.parkingapp.userservice.infrastructure.database;

import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.UsersRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.util.Collections.emptyMap;

public class JdbcUsersRepository implements UsersRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcUsersRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

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
    public Optional<User> getUserById(UUID userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return namedParameterJdbcTemplate.query(
                """
                   SELECT * from users
                   WHERE id = :userId
                """,
                params,
                new UserRowMapper()
        )
                .stream().findFirst();
    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getString("password")
            );
        }
    }
}
