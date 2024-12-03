package com.second.fiascofestival.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document(collection = "users")
public class User {
    @Id
    private String id;
   // @Field("username")
    private String username;
    private String password;
    private String fullName;

    // Constructors, Getters, Setters, etc.

    public User() {}

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

}
