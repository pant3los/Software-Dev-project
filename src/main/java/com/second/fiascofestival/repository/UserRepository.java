package com.second.fiascofestival.repository;

import com.second.fiascofestival.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    // Find user by username
    Optional<User> findByUsername(String username);
    Optional<User> findById(String id); // Add this if missing

}
