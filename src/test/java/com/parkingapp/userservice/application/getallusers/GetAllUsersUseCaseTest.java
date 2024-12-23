package com.parkingapp.userservice.application.getallusers;

import com.parkingapp.userservice.domain.user.Roles;
import com.parkingapp.userservice.domain.user.User;
import com.parkingapp.userservice.domain.user.UsersRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetAllUsersUseCaseTest {

    private final UsersRepository usersRepository = mock(UsersRepository.class);
    private final GetAllUsersUseCase useCase = new GetAllUsersUseCase(usersRepository);

    @Test
    void shouldReturnAllUsers() {
        // GIVEN
        List<User> usersList = List.of(new User(UUID.randomUUID(), "name", "lastname", "dummy@email.com", "1234", Roles.USER));
        when(usersRepository.getAllUsers()).thenReturn(usersList);

        // WHEN
        List<User> result = useCase.execute();

        // THEN
        assertThat(result).isNotEmpty().isEqualTo(usersList);
        verify(usersRepository, times(1)).getAllUsers();
    }

    @Test
    void shouldReturnEmptyListIfIsNotUsersSaved() {
        // GIVEN
        when(usersRepository.getAllUsers()).thenReturn(List.of());

        // WHEN
        List<User> result = useCase.execute();

        // THEN
        assertThat(result).isEmpty();
        verify(usersRepository, times(1)).getAllUsers();
    }
}