package com.parkingapp.userservice.application.registeruser;

import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.RegisterFailure;
import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.Successful;
import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.UserAlreadyExist;
import com.parkingapp.userservice.domain.service.PasswordEncryptor;
import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.UsersRepository;

public class RegisterUserUseCase {
    private final UsersRepository usersRepository;
    private final PasswordEncryptor passwordEncryptor;

    public RegisterUserUseCase(
        UsersRepository usersRepository,
        PasswordEncryptor passwordEncryptor
    ) {
        this.usersRepository = usersRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    public RegisterUserResponse execute(User user) {
        boolean userAlreadyExist = usersRepository.getUserByEmail(user.getEmail()).isPresent();

        if (userAlreadyExist) {
            return new UserAlreadyExist();
        }

        String encryptedPassword = passwordEncryptor.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);
        boolean isSaved = usersRepository.save(user);
        return isSaved ? new Successful(user) : new RegisterFailure();
    }
}
