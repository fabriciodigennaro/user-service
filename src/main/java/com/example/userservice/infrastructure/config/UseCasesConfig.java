package com.example.userservice.infrastructure.config;

import com.example.userservice.application.getAllUsers.GetAllUsersUseCase;
import com.example.userservice.domain.user.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public GetAllUsersUseCase getAllUsersUseCase(
            UsersRepository usersRepository
    ) {
        return new GetAllUsersUseCase(usersRepository);
    }
}

