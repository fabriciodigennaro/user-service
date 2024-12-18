package com.parkingapp.userservice.application.registeruser;

import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.RegisterFailure;
import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.Successful;
import com.parkingapp.userservice.application.registeruser.RegisterUserResponse.UserAlreadyExist;
import com.parkingapp.userservice.domain.service.PasswordEncryptor;
import com.parkingapp.userservice.domain.user.Roles;
import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.UsersRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegisterUserUseCaseTest {

    private final UsersRepository usersRepository = mock(UsersRepository.class);
    private final PasswordEncryptor passwordEncryptor = mock(PasswordEncryptor.class);
    private final RegisterUserUseCase useCase = new RegisterUserUseCase(usersRepository, passwordEncryptor);

    User user = new User(
        UUID.randomUUID(),
        "name",
        "lastname",
        "dummy@email.com",
        "1234",
        Roles.USER
    );

    @Test
    void shouldSaveANewUser() {
        // GIVEN
        when(usersRepository.save(user)).thenReturn(true);

        // WHEN
        RegisterUserResponse result = useCase.execute(user);

        // THEN
        assertThat(result).isInstanceOf(Successful.class);
        verify(usersRepository, times(1)).save(user);
    }

    @Test
    void shouldReturnARegisterFailureIsUserCannotBeSaved() {
        // GIVEN
        when(usersRepository.save(user)).thenReturn(false);

        // WHEN
        RegisterUserResponse result = useCase.execute(user);

        // THEN
        assertThat(result).isInstanceOf(RegisterFailure.class);
        verify(usersRepository, times(1)).save(user);
    }

    @Test
    void shouldReturnAUserAlreadyExistIsUserAlreadyExists() {
        // GIVEN
        when(usersRepository.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(usersRepository.save(user)).thenReturn(false);

        // WHEN
        RegisterUserResponse result = useCase.execute(user);

        // THEN
        assertThat(result).isInstanceOf(UserAlreadyExist.class);
        verify(usersRepository, times(1)).getUserByEmail(user.getEmail());
    }
}