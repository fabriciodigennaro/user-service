package com.example.userservice.infrastructure.database;

import com.example.userservice.domain.user.User;
import com.example.userservice.domain.user.UsersRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

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
