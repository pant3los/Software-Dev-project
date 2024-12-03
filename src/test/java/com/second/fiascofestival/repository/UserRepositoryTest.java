package com.second.fiascofestival.repository;

import com.second.fiascofestival.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password123");
        userRepository.save(user);

        // Act
        Optional<User> retrievedUser = userRepository.findByUsername("testUser");

        // Assert
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getUsername()).isEqualTo("testUser");
    }
    @Test
    void testSaveAndFindUser() {
        // Arrange
        User user = new User();
        user.setUsername("repoTestUser");
        user.setPassword("testPassword");

        // Act
        User savedUser = userRepository.save(user);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("repoTestUser");
    }

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }
}
