package org.example.userstoragespi.repository.interfaces;

import org.example.userstoragespi.domain.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
    User getUserByUsername(String username);
    List<User> searchUser(String username);
}
