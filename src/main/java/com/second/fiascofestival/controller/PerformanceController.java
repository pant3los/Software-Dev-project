package com.second.fiascofestival.controller;

import com.second.fiascofestival.enums.PerformanceState;
import com.second.fiascofestival.model.Performance;
import com.second.fiascofestival.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    // Create a new performance
    @PostMapping
    public ResponseEntity<?> createPerformance(@RequestBody Performance performance, @RequestHeader("user-role") String role) {
        try {
            // Validate user role
            if (!role.equalsIgnoreCase("ORGANIZER")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only ORGANIZER can create performances.");
            }

            // Validate required fields
            if (performance.getName() == null || performance.getDescription() == null || performance.getGenre() == null) {
                return ResponseEntity.badRequest().body("Missing required fields: name, description, or genre.");
            }

            // Set default state and save
            performance.setState(PerformanceState.CREATED); // Default state
            Performance createdPerformance = performanceService.createPerformance(performance);
            return ResponseEntity.ok(createdPerformance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating performance: " + e.getMessage());
        }
    }
    @PutMapping("/{id}/submit")
    public ResponseEntity<?> submitPerformance(@PathVariable String id, @RequestHeader("user-role") String role) {
        try {
            // Validate user role
            if (!role.equalsIgnoreCase("ORGANIZER")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only ORGANIZER can submit performances.");
            }

            // Change state to SUBMITTED
            Performance submittedPerformance = performanceService.submitPerformance(id);
            return ResponseEntity.ok(submittedPerformance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting performance: " + e.getMessage());
        }
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchPerformances(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String festivalId) {
        try {
            List<Performance> results = performanceService.searchPerformances(genre, festivalId);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error searching performances: " + e.getMessage());
        }
    }


    // Update an existing performance
    @PutMapping("/{id}")
    public ResponseEntity<Performance> updatePerformance(@PathVariable String id, @RequestBody Performance updatedPerformance) {
        return ResponseEntity.ok(performanceService.updatePerformance(id, updatedPerformance));
    }

    // Get all performances by festival ID
    @GetMapping("/festival/{festivalId}")
    public ResponseEntity<List<Performance>> getPerformancesByFestival(@PathVariable String festivalId) {
        return ResponseEntity.ok(performanceService.getPerformancesByFestival(festivalId));
    }

    // Delete a performance by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable String id) {
        performanceService.deletePerformance(id);
        return ResponseEntity.noContent().build();
    }




}
