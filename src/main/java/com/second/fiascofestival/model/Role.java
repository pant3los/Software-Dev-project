package com.second.fiascofestival.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    private String userId; // Reference to User
    private String festivalId; // Reference to Festival
    private String role; // Possible values: VISITOR, ARTIST, ORGANIZER, STAFF

    // Constructors, Getters, Setters, etc.

    public Role() {}

    public Role(String userId, String festivalId, String role) {
        this.userId = userId;
        this.festivalId = festivalId;
        this.role = role;
    }

}
