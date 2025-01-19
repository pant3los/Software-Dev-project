package com.second.fiascofestival.model;

import com.second.fiascofestival.enums.FestivalState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;

@Setter
@Getter
@Document(collection = "festivals")
public class Festival {
    @Id
    private String id;
    private LocalDate created = LocalDate.now();
    @Indexed(unique = true)
    @NotNull(message = "Festival's name must not be null")
    private String name;

    @NotNull(message = "Festival's description must not be null")
    private String description;

    @NotNull(message = "Festival's description must not be null")
    private Date startDate;

    @NotNull(message = "Festival's description must not be null")
    private Date endDate;

    @NotNull(message = "Festival's description must not be null")
    private String venue;

    private String budget;
    private String vendorManagement;
    private FestivalState state = FestivalState.CREATED; // Possible values: CREATED, SUBMISSION, ASSIGNMENT, etc.

    private HashSet<User> organizers = new HashSet<>();
    private HashSet<Performance> performances = new HashSet<>();
    private HashSet<User> staff = new HashSet<>();


    // Constructors, Getters, Setters, etc.

    public Festival() {
    }

    public Festival(String name, String description, Date startDate, Date endDate, String venue, FestivalState state) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
        this.state = state;
    }

}
