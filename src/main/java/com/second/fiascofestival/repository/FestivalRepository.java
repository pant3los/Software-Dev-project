package com.second.fiascofestival.repository;


import com.second.fiascofestival.model.Festival;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FestivalRepository extends MongoRepository<Festival, String> {
    // Find festivals by name containing a keyword (case-insensitive)
    List<Festival> findByNameContainingIgnoreCase(String name);

    // Find festivals by state
    List<Festival> findByState(String state);
}
