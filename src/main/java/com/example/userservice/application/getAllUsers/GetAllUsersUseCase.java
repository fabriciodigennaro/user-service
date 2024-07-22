package com.example.userservice.application.getAllUsers;

import com.example.userservice.domain.user.User;
import com.example.userservice.domain.user.UsersRepository;

import java.util.List;

public class GetAllUsersUseCase {
    private final UsersRepository usersRepository;

    public GetAllUsersUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> execute() {
        return usersRepository.getAllUsers();
    }
}
