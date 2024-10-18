package com.parkingapp.userservice.application.getuserbyid;

import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.UsersRepository;

import java.util.Optional;
import java.util.UUID;

public class GetUserByIdUseCase {
    private final UsersRepository usersRepository;

    public GetUserByIdUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<User> execute(UUID id) {
        return usersRepository.getUserById(id);
    }
}
