package com.parkingapp.userservice.infrastructure.config;

import com.parkingapp.userservice.application.getallusers.GetAllUsersUseCase;
import com.parkingapp.userservice.application.getuserbyemail.GetUserByEmailUseCase;
import com.parkingapp.userservice.application.registeruser.RegisterUserUseCase;
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
    public GetUserByEmailUseCase getUserByIdUseCase(
        UsersRepository usersRepository
    ) {
        return new GetUserByEmailUseCase(usersRepository);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(
        UsersRepository usersRepository
    ) {
        return new RegisterUserUseCase(usersRepository);
    }
}

