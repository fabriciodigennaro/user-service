package com.parkingapp.userservice.domain.user;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    List<User> getAllUsers();
    Optional<User> getUserByEmail(String email);
    boolean save(User user);
}
