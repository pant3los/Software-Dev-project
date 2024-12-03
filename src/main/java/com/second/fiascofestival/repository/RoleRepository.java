package com.second.fiascofestival.repository;


import com.second.fiascofestival.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoleRepository extends MongoRepository<Role, String> {
    // Find roles by user ID
    List<Role> findByUserId(String userId);

    // Find roles by festival ID
    List<Role> findByFestivalId(String festivalId);

    // Find roles by user ID and festival ID
    List<Role> findByUserIdAndFestivalId(String userId, String festivalId);
}
