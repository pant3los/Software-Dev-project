package com.second.fiascofestival.service;

import com.second.fiascofestival.exceptions.UserException;
import com.second.fiascofestival.model.User;
import com.second.fiascofestival.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId("1");
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setFullName("Test User");
        System.out.println("Setup complete: Initialized user object for testing.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test completed.\n");
    }

    @Test
    void registerUser_UserAlreadyExists_ThrowsException() {
        System.out.println("Running: registerUser_UserAlreadyExists_ThrowsException");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        }, "Expected an IllegalArgumentException to be thrown when the username already exists.");

        assertEquals("Username already exists.", exception.getMessage(), "Exception message should match expected output.");
        verify(userRepository, never()).save(any(User.class));

        System.out.println("Test passed: User registration fails if the username already exists.");
    }

    @Test
    void registerUser_SuccessfulRegistration() {
        System.out.println("Running: registerUser_SuccessfulRegistration");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser, "Registered user should not be null.");
        assertEquals(user.getUsername(), registeredUser.getUsername(), "Username of registered user should match input.");
        verify(userRepository).save(user);

        System.out.println("Test passed: User registration is successful for a new username.");
    }

    @Test
    void getUserById_UserNotFound_ThrowsException() {
        System.out.println("Running: getUserById_UserNotFound_ThrowsException");

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(user.getId());
        }, "Expected a RuntimeException to be thrown when the user ID is not found.");

        assertEquals("User not found with id: " + user.getId(), exception.getMessage(), "Exception message should match expected output.");

        System.out.println("Test passed: Retrieving user by ID fails when the user is not found.");
    }

    @Test
    void getUserById_SuccessfulRetrieval() {
        System.out.println("Running: getUserById_SuccessfulRetrieval");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(user.getId());

        assertNotNull(foundUser, "Found user should not be null.");
        assertEquals(user.getId(), foundUser.getId(), "User ID of the found user should match input.");

        System.out.println("Test passed: User retrieval by ID is successful.");
    }

    @Test
    void authenticateUser_InvalidCredentials_ThrowsException() {
        System.out.println("Running: authenticateUser_InvalidCredentials_ThrowsException");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticateUser(user.getUsername(), "wrongpassword");
        }, "Expected an IllegalArgumentException to be thrown for invalid credentials.");

        assertEquals("Invalid username or password.", exception.getMessage(), "Exception message should match expected output.");

        System.out.println("Test passed: Authentication fails with invalid credentials.");
    }

    @Test
    void authenticateUser_SuccessfulAuthentication() {
        System.out.println("Running: authenticateUser_SuccessfulAuthentication");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());

        assertNotNull(authenticatedUser, "Authenticated user should not be null.");
        assertEquals(user.getUsername(), authenticatedUser.getUsername(), "Username of the authenticated user should match input.");

        System.out.println("Test passed: Authentication is successful with valid credentials.");
    }

    @Test
    void validateUser_NoAuthHeader_ThrowsUserException() {
        System.out.println("Running: validateUser_NoAuthHeader_ThrowsUserException");

        UserException exception = assertThrows(UserException.class, () -> {
            userService.validateUser(null);
        }, "Expected a UserException to be thrown when no auth header is provided.");

        assertEquals("No authheader provided", exception.getMessage(), "Exception message should match expected output.");

        System.out.println("Test passed: Validation fails when no auth header is provided.");
    }

    @Test
    void validateUser_InvalidAuthHeaderFormat_ThrowsUserException() {
        System.out.println("Running: validateUser_InvalidAuthHeaderFormat_ThrowsUserException");

        String invalidAuthHeader = "InvalidFormat";

        UserException exception = assertThrows(UserException.class, () -> {
            userService.validateUser(invalidAuthHeader);
        }, "Expected a UserException to be thrown for an invalid auth header format.");

        assertEquals("No authheader provided", exception.getMessage(), "Exception message should match expected output.");

        System.out.println("Test passed: Validation fails for an invalid auth header format.");
    }

    @Test
    void validateUser_InvalidUsername_ThrowsUserException() {
        System.out.println("Running: validateUser_InvalidUsername_ThrowsUserException");

        String authHeader = "Basic " + Base64.getEncoder().encodeToString("invaliduser:password123".getBytes());

        when(userRepository.findByUsername("invaliduser")).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> {
            userService.validateUser(authHeader);
        }, "Expected a UserException to be thrown when the username does not exist.");

        assertEquals("Username does not exists", exception.getMessage(), "Exception message should match expected output.");

        System.out.println("Test passed: Validation fails for a non-existent username.");
    }

    @Test
    void validateUser_InvalidPassword_ThrowsUserException() {
        System.out.println("Running: validateUser_InvalidPassword_ThrowsUserException");

        String authHeader = "Basic " + Base64.getEncoder().encodeToString("testuser:wrongpassword".getBytes());

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        UserException exception = assertThrows(UserException.class, () -> {
            userService.validateUser(authHeader);
        }, "Expected a UserException to be thrown for an invalid password.");

        assertEquals("Invalid username or password.", exception.getMessage(), "Exception message should match expected output.");

        System.out.println("Test passed: Validation fails for an invalid password.");
    }

    @Test
    void validateUser_SuccessfulValidation() {
        System.out.println("Running: validateUser_SuccessfulValidation");

        String authHeader = "Basic " + Base64.getEncoder().encodeToString("testuser:password123".getBytes());

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User validatedUser = null;
        try {
            validatedUser = userService.validateUser(authHeader);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(validatedUser, "Validated user should not be null.");
        assertEquals(user.getUsername(), validatedUser.getUsername(), "Username of the validated user should match input.");

        System.out.println("Test passed: Validation is successful for valid credentials.");
    }
}
