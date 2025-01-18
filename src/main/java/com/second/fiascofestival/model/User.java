package com.second.fiascofestival.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;

@Setter
@Getter
@Document(collection = "users")
public class User {
    @Id
    private String id;
   // @Field("username")
   @NotNull(message = "User's username must not be null")
    private String username;
    @NotNull(message = "User's password must not be null")
    private String password;
    @NotNull(message = "User's fullname must not be null")
    private String fullName;

    private HashSet<String> main_artisti_in = new HashSet<>();
    private HashSet<String> artist_in = new HashSet<>();
    private HashSet<String> stage_manager = new HashSet<>();
    private HashSet<String> organizer_in = new HashSet<>();
    private HashSet<String> staff_in = new HashSet<>();

    // Constructors, Getters, Setters, etc.

    public User() {}

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

}
