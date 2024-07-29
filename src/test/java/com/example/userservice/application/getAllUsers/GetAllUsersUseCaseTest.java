package com.example.userservice.application.getAllUsers;

import com.example.userservice.domain.user.User;
import com.example.userservice.domain.user.UsersRepository;
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
        List<User> users = List.of(new User(UUID.randomUUID(), "name", "lastname", "dummy@email.com", "1234"));
        when(usersRepository.getAllUsers()).thenReturn(users);

        // WHEN
        List<User> result = useCase.execute();

        // THEN
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(users);
        verify(usersRepository, times(5)).getAllUsers();
    }
}