package com.openclassrooms.poseidon.service;


import com.openclassrooms.poseidon.entity.User;
import com.openclassrooms.poseidon.exception.UserNotFoundException;
import com.openclassrooms.poseidon.repository.UserRepository;
import com.openclassrooms.poseidon.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void getAllUsersShouldReturnList() {

        List<User> usersList = Arrays.asList(new User(), new User());

        when(userRepository.findAll()).thenReturn(usersList);

        List<User> result = userService.getAllUsers();

        assertEquals(usersList, result);
        verify(userRepository, times(1)).findAll();

    }

    @Test
    void getUserByIdCasPassant() {

        int id = 1;
        User user = new User();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getUserById(id);

        assertEquals(user, result);
    }

    @Test
    void getUserByIdCasNonPassant() {

        int id = 2;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(UserNotFoundException.class, () -> {
           userService.getUserById(id);
        });
        assertEquals("L'utilisateur n'a pas été trouvé avec l'id " + id, e.getMessage());

    }

    @Test
    void createUserSuccess() {

        User newUser = new User();
        newUser.setUsername("Username");
        newUser.setPassword("1234");
        newUser.setFullname("Fullname");
        newUser.setRole("USER");

        User userInBdd = new User();
        userInBdd.setId(1);
        userInBdd.setUsername("Username");
        userInBdd.setPassword("PasswordEncodé");
        userInBdd.setFullname("Fullname");
        userInBdd.setRole("USER");

        when(userRepository.findByUsername("Username")).thenReturn(null);
        when(passwordEncoder.encode("1234")).thenReturn("PasswordEncodé");
        when(userRepository.save(any(User.class))).thenReturn(userInBdd);

        User result = userService.createUser(newUser);

        assertEquals("Username", result.getUsername());
        assertEquals(1, result.getId());
        assertEquals("PasswordEncodé", result.getPassword());
        assertEquals("Fullname", result.getFullname());
        assertEquals("USER", result.getRole());

        verify(userRepository, times(1)).save(newUser);
        verify(userRepository).findByUsername("Username");
    }

    @Test
    void updateUserShouldReturnAnUpdatedUser() {

        Integer id = 1;

        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setUsername("Username");
        existingUser.setPassword("1234");
        existingUser.setFullname("Fullname");
        existingUser.setRole("USER");

        User userUpdated = new User();
        userUpdated.setUsername("updatedUsername");
        userUpdated.setPassword("updatedPassword");
        userUpdated.setFullname("updatedFullname");
        userUpdated.setRole(" ADMIN ");

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername("updatedUsername")).thenReturn(null);
        when(passwordEncoder.encode("updatedPassword")).thenReturn("updatedPasswordEncodé");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateUser(id, userUpdated);

        assertEquals("updatedUsername", result.getUsername());
        assertEquals("updatedPasswordEncodé", result.getPassword());
        assertEquals("updatedFullname", result.getFullname());
        assertEquals("ADMIN", result.getRole());

        verify(userRepository, times(1)).save(existingUser);
        verify(userRepository).findByUsername("updatedUsername");

    }

    @Test
    void deleteUserReturnTrue() {

        int id = 1;
        User existingUser = new User();
        existingUser.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        doNothing().when(userRepository).delete(existingUser);

        boolean result = userService.deleteUser(id);

        assertTrue(result);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).delete(existingUser);

    }

    @Test
    public void deleteUserShouldThrowAnExceptionWhenUserNotFound() {

        Integer id = 9;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(id);
        });

        assertEquals("L'utilisateur n'a pas été trouvé avec l'id " + id, e.getMessage());

        verify(userRepository, times(1)).findById(id);

    }

}
