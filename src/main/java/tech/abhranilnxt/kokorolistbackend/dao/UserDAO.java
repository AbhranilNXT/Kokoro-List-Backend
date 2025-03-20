package tech.abhranilnxt.kokorolistbackend.dao;

import tech.abhranilnxt.kokorolistbackend.entity.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> getUserById(String userId);

    void saveOrUpdateUser(User user);
    void deleteUser(User user);
}
