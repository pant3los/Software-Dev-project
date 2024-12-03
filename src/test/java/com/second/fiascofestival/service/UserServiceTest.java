package com.second.fiascofestival.service;


import com.second.fiascofestival.model.User;
import com.second.fiascofestival.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccess() {
        User user = new User("testUser", "password123", "Test User");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
    }

    @Test
    void testRegisterUserDuplicateUsername() {
        User user = new User("testUser", "password123", "Test User");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("Username already exists.", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void testAuthenticateUserSuccess() {
        User user = new User("testUser", "password123", "Test User");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        User authenticatedUser = userService.authenticateUser("testUser", "password123");

        assertNotNull(authenticatedUser);
        assertEquals("testUser", authenticatedUser.getUsername());
    }

    @Test
    void testAuthenticateUserInvalidPassword() {
        User user = new User("testUser", "password123", "Test User");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticateUser("testUser", "wrongPassword");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
    }

    @Test
    void testAuthenticateUserNotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticateUser("unknownUser", "password123");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
    }
}
