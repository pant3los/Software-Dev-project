package com.second.fiascofestival.inter;

import com.second.fiascofestival.model.User;
import com.second.fiascofestival.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

    @Autowired
    private Environment environment;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setup() {
        userRepository.deleteAll(); // Clear data before each test
    }

    @Test
    void testUserRegistration() {
        // Arrange
        User user = new User();
        user.setUsername("integrationTestUser");
        user.setPassword("password123");

        // Get the dynamic port from the environment
        String port = environment.getProperty("local.server.port");

        // Act
        User registeredUser = this.restTemplate
                .postForObject("http://localhost:" + port + "/api/users/register", user, User.class);

        // Assert
        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getUsername()).isEqualTo("integrationTestUser");
    }
    @Test
    void testGetUserById() {
        // Arrange
        User user = new User("testGetUser", "password123", "Test User");
        User savedUser = userRepository.save(user);
        //System.out.println("Saved User: " + savedUser);
        //System.out.println(savedUser.getId());

        // Act
        String url = "http://localhost:" + environment.getProperty("local.server.port") + "/api/users/" + savedUser.getId();
        User fetchedUser = restTemplate.getForObject(url, User.class);

        // Assert
       // System.out.println("Fetched User: " + fetchedUser);
        //System.out.println("Fetched User ID: " + fetchedUser.getId());
        //System.out.println("Fetched User Username: " + fetchedUser.getUsername());
        //System.out.println("Fetched User Full Name: " + fetchedUser.getFullName());

        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getUsername()).isEqualTo("testGetUser");
        assertThat(fetchedUser.getFullName()).isEqualTo("Test User");
    }



}
