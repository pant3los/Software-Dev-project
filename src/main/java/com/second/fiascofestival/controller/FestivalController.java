package com.second.fiascofestival.controller;

import com.second.fiascofestival.model.Festival;
import com.second.fiascofestival.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/festivals")
public class FestivalController {

    @Autowired
    private FestivalService festivalService;

    // Create a new festival
    @PostMapping
    public ResponseEntity<Festival> createFestival(@RequestBody Festival festival) {
        return ResponseEntity.ok(festivalService.createFestival(festival));
    }

    // Update an existing festival
    @PutMapping("/{id}")
    public ResponseEntity<Festival> updateFestival(@PathVariable String id, @RequestBody Festival updatedFestival) {
        return ResponseEntity.ok(festivalService.updateFestival(id, updatedFestival));
    }

    // Get all festivals
    @GetMapping
    public ResponseEntity<List<Festival>> getAllFestivals() {
        return ResponseEntity.ok(festivalService.getAllFestivals());
    }

    // Delete a festival by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFestival(@PathVariable String id) {
        festivalService.deleteFestival(id);
        return ResponseEntity.noContent().build();
    }
}
