package com.parkingapp.userservice.application.getuserbyemail;

import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.UsersRepository;

import java.util.Optional;

public class GetUserByEmailUseCase {
    private final UsersRepository usersRepository;

    public GetUserByEmailUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<User> execute(String email) {
        return usersRepository.getUserByEmail(email);
    }
}
