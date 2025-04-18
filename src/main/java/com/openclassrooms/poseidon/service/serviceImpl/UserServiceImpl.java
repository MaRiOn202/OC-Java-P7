package com.openclassrooms.poseidon.service.serviceImpl;

import com.openclassrooms.poseidon.domain.User;
import com.openclassrooms.poseidon.exception.UserNotFoundException;
import com.openclassrooms.poseidon.repositories.UserRepository;
import com.openclassrooms.poseidon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class UserServiceImpl implements UserService {

    // injection par constructeur + fiable 
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        "L'utilisateur n'a pas été trouvé avec l'id " + id));
    }

    @Override
    public User createUser(User user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Ce username est déjà pris");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Integer id, User userUpdated) {

        User existingUser = getUserById(id);
        existingUser.setUsername(userUpdated.getUsername());
        existingUser.setFullname(userUpdated.getFullname());
        existingUser.setRole(userUpdated.getRole());

        if (userUpdated.getPassword() != null && !userUpdated.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userUpdated.getPassword()));

        }
        userRepository.save(existingUser);
        return existingUser;
    }

    @Override
    public boolean deleteUser(Integer id) {
        User user = getUserById(id);
        userRepository.delete(user);
        return true;
    }


    @Override
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }




}
