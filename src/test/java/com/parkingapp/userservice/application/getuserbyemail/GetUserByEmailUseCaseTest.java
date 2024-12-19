package com.parkingapp.userservice.application.getuserbyemail;

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

class GetUserByEmailUseCaseTest {
    private final UsersRepository usersRepository = mock(UsersRepository.class);
    private final GetUserByEmailUseCase useCase = new GetUserByEmailUseCase(usersRepository);

    @Test
    void shouldReturnAUserSearchingById() {
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
        when(usersRepository.getUserByEmail(email)).thenReturn(Optional.of(user));

        // WHEN
        Optional<User> result = useCase.execute(email);

        // THEN
        assertThat(result).isPresent();
        assertThat(result).isEqualTo(Optional.of(user));
        assertThat(result.get().getId()).isEqualTo(userId);
        verify(usersRepository, times(1)).getUserByEmail(email);
    }

    @Test
    void shouldReturnAEmptyOptionalWhenUserNotFound() {
        // GIVEN
        String email = "dummy@email.com";
        when(usersRepository.getUserByEmail(email)).thenReturn(Optional.empty());

        // WHEN
        Optional<User> result = useCase.execute(email);

        // THEN
        assertThat(result).isEmpty();
        verify(usersRepository, times(1)).getUserByEmail(email);
    }

}