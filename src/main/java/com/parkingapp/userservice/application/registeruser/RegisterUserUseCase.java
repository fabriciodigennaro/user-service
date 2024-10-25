package com.parkingapp.userservice.application.registeruser;

import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.RegisterFailure;
import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.Successful;
import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.UserAlreadyExist;
import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.UsersRepository;

public class RegisterUserUseCase {
    private final UsersRepository usersRepository;

    public RegisterUserUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public RegisterUserResponse execute(User user) {
        boolean userAlreadyExist = usersRepository.getUserByEmail(user.getEmail()).isPresent();

        if (userAlreadyExist) {
            return new UserAlreadyExist();
        }

        boolean isSaved = usersRepository.save(user);
        return isSaved ? new Successful(user) : new RegisterFailure();
    }
}
