package com.parkingapp.userservice.application.getuserbyid;

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

class GetUserByIdUseCaseTest {
    private final UsersRepository usersRepository = mock(UsersRepository.class);
    private final GetUserByIdUseCase useCase = new GetUserByIdUseCase(usersRepository);

    @Test
    void shouldGetAUserById() {
        // GIVEN
        UUID userId = UUID.randomUUID();
        String email = "dummy@email.com";
        User user = new User(
            userId,
            "name",
            "lastname",
            email,
            "1234",
            Roles.USER
        );
        when(usersRepository.getUserById(userId)).thenReturn(Optional.of(user));

        // WHEN
        Optional<User> result = useCase.execute(userId);

        // THEN
        assertThat(result).isPresent();
        assertThat(result).isEqualTo(Optional.of(user));
        assertThat(result.get().getId()).isEqualTo(userId);
        verify(usersRepository, times(1)).getUserById(userId);
    }

    @Test
    void shouldReturnAEmptyOptionalWhenUserNotFound() {
        // GIVEN
        UUID wrongUserId = UUID.randomUUID();
        when(usersRepository.getUserById(wrongUserId)).thenReturn(Optional.empty());

        // WHEN
        Optional<User> result = useCase.execute(wrongUserId);

        // THEN
        assertThat(result).isEmpty();
        verify(usersRepository, times(1)).getUserById(wrongUserId);
    }
}