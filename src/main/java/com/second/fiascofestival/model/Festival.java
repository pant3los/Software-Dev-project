package com.second.fiascofestival.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@Document(collection = "festivals")
public class Festival {
    @Id
    private String id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String venue;
    private String state; // Possible values: CREATED, SUBMISSION, ASSIGNMENT, etc.

    // Constructors, Getters, Setters, etc.

    public Festival() {}

    public Festival(String name, String description, Date startDate, Date endDate, String venue, String state) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
        this.state = state;
    }

}
