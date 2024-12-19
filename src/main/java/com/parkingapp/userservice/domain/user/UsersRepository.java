package com.parkingapp.userservice.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersRepository {
    List<User> getAllUsers();
    Optional<User> getUserById(UUID userId);
    Optional<User> getUserByEmail(String email);
    boolean save(User user);
}
