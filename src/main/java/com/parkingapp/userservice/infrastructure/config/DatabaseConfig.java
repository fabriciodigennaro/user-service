package com.parkingapp.userservice.infrastructure.config;


import com.parkingapp.userservice.domain.user.UsersRepository;
import com.parkingapp.userservice.infrastructure.database.JdbcUsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfig {

    @Bean
    public UsersRepository usersRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcUsersRepository(namedParameterJdbcTemplate);
    }
}

