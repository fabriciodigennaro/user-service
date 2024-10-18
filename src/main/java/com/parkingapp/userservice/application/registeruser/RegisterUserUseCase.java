package com.parkingapp.userservice.application.registeruser;

import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.UsersRepository;

import java.util.Optional;

public class RegisterUserUseCase {
    private final UsersRepository usersRepository;


    public RegisterUserUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<User> execute(User user) {
        boolean userNotExist = usersRepository.getUserByEmail(user.getEmail()).isEmpty();

        if (userNotExist) {
            boolean isSaved = usersRepository.save(user);
            return isSaved ? Optional.of(user) : Optional.empty();
        }
        return Optional.empty();
    }
}
