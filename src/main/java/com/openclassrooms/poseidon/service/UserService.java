package com.openclassrooms.poseidon.service;

import com.openclassrooms.poseidon.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Integer id);

    User createUser(User user);

    User updateUser(Integer id, User userUpdated);

    boolean deleteUser(Integer id);

    User getUserByUsername(String username);
}
