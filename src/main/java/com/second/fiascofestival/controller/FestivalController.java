package com.second.fiascofestival.controller;

import com.second.fiascofestival.exceptions.FestivalException;
import com.second.fiascofestival.exceptions.UserException;
import com.second.fiascofestival.model.Festival;
import com.second.fiascofestival.model.User;
import com.second.fiascofestival.service.FestivalService;
import com.second.fiascofestival.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/festivals")
public class FestivalController {

    @Autowired
    private FestivalService festivalService;
    @Autowired
    private UserService userService;

    // Create a new festival
    @PostMapping
    public ResponseEntity<?> createFestival(@RequestHeader("Authorization") String authHeader, @RequestBody Festival festival) {

        User loggedUser = null;
        try {
            loggedUser = userService.validateUser(authHeader);
        } catch (UserException e) {
            return ResponseEntity.status(400).body("wrong username or password");
        }

        try {
            return ResponseEntity.ok(festivalService.createFestival(festival, loggedUser));
        } catch (FestivalException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Update an existing festival
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id, @RequestBody Festival updatedFestival) {
        User loggedUser = null;
        try {
            loggedUser = userService.validateUser(authHeader);
        } catch (UserException e) {
            return ResponseEntity.status(400).body("wrong username or password");
        }

        return ResponseEntity.ok(festivalService.updateFestival(id, updatedFestival));
    }

    // Get all festivals
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllFestivals(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(festivalService.getAllFestivals());
    }

    // Delete a festival by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        festivalService.deleteFestival(id);
        return ResponseEntity.ok("Festival Deleted successfully");
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        festivalService.submitFestival(id);
        return ResponseEntity.ok("Festival submited successfully");
    }
    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assignFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        festivalService.assignFestival(id);
        return ResponseEntity.ok("Festival assigned successfully");
   }
    @PostMapping("/{id}/review")
    public ResponseEntity<?> reviewFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        festivalService.reviewFestival(id);
        return ResponseEntity.ok("Festival got in review successfully");
    }
    @PostMapping("/{id}/schedule")
    public ResponseEntity<?> scheduleFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        festivalService.scheduleFestival(id);
        return ResponseEntity.ok("Festival got in schedule state successfully");
    }
    @PostMapping("/{id}/final")
    public ResponseEntity<?> finalFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        festivalService.finalFestival(id);
        return ResponseEntity.ok("Festival got in final state successfully");
    }
    @PostMapping("/{id}/decision")
    public ResponseEntity<?> decisionFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        festivalService.decisionFestival(id);
        return ResponseEntity.ok("Festival got in decision successfully");
    }
    @PostMapping("/{id}/announced")
    public ResponseEntity<?> announcedFestival(@RequestHeader("Authorization") String authHeader,@PathVariable String id) {
        festivalService.announcedFestival(id);
        return ResponseEntity.ok("Festival got in announced successfully");
    }

}
