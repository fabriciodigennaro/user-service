package com.parkingapp.userservice.infrastructure.config;

import com.parkingapp.userservice.application.getAllUsers.GetAllUsersUseCase;
import com.parkingapp.userservice.application.getUserById.GetUserByIdUseCase;
import com.parkingapp.userservice.domain.user.UsersRepository;
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

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(
            UsersRepository usersRepository
    ) {
        return new GetUserByIdUseCase(usersRepository);
    }
}

