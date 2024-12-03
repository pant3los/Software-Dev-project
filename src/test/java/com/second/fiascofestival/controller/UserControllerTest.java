package com.second.fiascofestival.controller;

import com.second.fiascofestival.model.User;
import com.second.fiascofestival.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void testRegisterUserSuccess() throws Exception {
        User user = new User("testUser", "password123", "Test User");
        when(userService.registerUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "username": "testUser",
                            "password": "password123",
                            "fullName": "Test User"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "username": "testUser",
                            "password": "password123",
                            "fullName": "Test User"
                        }
                        """));
    }

    @Test
    void testRegisterUserDuplicateUsername() throws Exception {
        when(userService.registerUser(any(User.class))).thenThrow(new IllegalArgumentException("Username already exists."));

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "username": "testUser",
                            "password": "password123",
                            "fullName": "Test User"
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username already exists."));
    }

    @Test
    void testLoginUserSuccess() throws Exception {
        User user = new User("testUser", "password123", "Test User");
        when(userService.authenticateUser("testUser", "password123")).thenReturn(user);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "testUser")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "username": "testUser",
                            "password": "password123",
                            "fullName": "Test User"
                        }
                        """));
    }

    @Test
    void testLoginUserInvalidCredentials() throws Exception {
        when(userService.authenticateUser("testUser", "wrongPassword"))
                .thenThrow(new IllegalArgumentException("Invalid username or password."));

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "testUser")
                        .param("password", "wrongPassword"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid username or password."));
    }
}
