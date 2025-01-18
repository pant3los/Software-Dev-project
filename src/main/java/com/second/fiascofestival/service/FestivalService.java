package com.second.fiascofestival.service;

import com.second.fiascofestival.enums.FestivalState;
import com.second.fiascofestival.exceptions.FestivalException;
import com.second.fiascofestival.model.Festival;
import com.second.fiascofestival.model.User;
import com.second.fiascofestival.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;

    public Festival createFestival(Festival festival, User loggeduser) throws FestivalException {

        if (festival.getName() == null || festival.getName().isEmpty()) {
            throw new FestivalException("Festival name is required.");
        }
        Optional<Festival> foundfestival = festivalRepository.findByName(festival.getName());
        if (foundfestival.isPresent()) {
            throw new FestivalException("Festival name already exists.");
        }

        festival.getOrganizers().add(loggeduser);
        loggeduser.getOrganizer_in().add(festival.getId());
        return festivalRepository.save(festival);

    }

    public Festival updateFestival(String id, Festival updatedFestival) {
        Festival existingFestival = festivalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festival not found."));
        existingFestival.setName(updatedFestival.getName());
        existingFestival.setDescription(updatedFestival.getDescription());
        existingFestival.setStartDate(updatedFestival.getStartDate());
        existingFestival.setEndDate(updatedFestival.getEndDate());
        return festivalRepository.save(existingFestival);
    }

    public List<Festival> getAllFestivals() {
        return festivalRepository.findAll();
    }

    public void deleteFestival(String id) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festival not found."));
        if (!"CREATED".equals(festival.getState())) {
            throw new IllegalStateException("Only festivals in CREATED state can be deleted.");
        }
        festivalRepository.deleteById(id);
    }
    public Optional<Festival> getFestivalById(String id) {
        return festivalRepository.findById(id);
    }

    public void submitFestival(String id) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festival not found."));
        if (festival.getState() != FestivalState.CREATED) {
            throw new IllegalStateException("Only festivals in CREATED state can be submitted.");
        }else{
            festival.setState(FestivalState.SUBMISSION);
            festivalRepository.save(festival);
        }
    }

    public void assignFestival(String id) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festival not found."));
        if (festival.getState() != FestivalState.SUBMISSION) {
            throw new IllegalStateException("Only festivals in SUBMITTED state can be assigned.");
        }else{
            festival.setState(FestivalState.ASSIGNMENT);
            festivalRepository.save(festival);
        }
    }

    public void reviewFestival(String id) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festival not found."));
        if (festival.getState() != FestivalState.ASSIGNMENT) {
            throw new IllegalStateException("Only festivals in ASSIGNMENT state can be reviewed.");
        }else{
            festival.setState(FestivalState.REVIEW);
            festivalRepository.save(festival);
        }
    }

    public void scheduleFestival(String id) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festival not found."));
        if (festival.getState() != FestivalState.REVIEW) {
            throw new IllegalStateException("Only festivals in REVIEW state can be scheduled.");
        }else{
            festival.setState(FestivalState.SCHEDULING);
            festivalRepository.save(festival);
        }
    }

    public void finalFestival(String id) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festival not found."));
        if (festival.getState() != FestivalState.FINAL_SUBMISSION) {
            throw new IllegalStateException("Only festivals in REVIEW state can be scheduled.");
        }else{
            festival.setState(FestivalState.FINAL_SUBMISSION);
            festivalRepository.save(festival);
        }
    }

    public void decisionFestival(String id) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festival not found."));
        if (festival.getState() != FestivalState.FINAL_SUBMISSION) {
            throw new IllegalStateException("Only festivals in REVIEW state can be scheduled.");
        }else{
            festival.setState(FestivalState.DECISION);
            festivalRepository.save(festival);
        }
    }

    public void announcedFestival(String id) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Festival not found."));
        if (festival.getState() != FestivalState.DECISION) {
            throw new IllegalStateException("Only festivals in REVIEW state can be scheduled.");
        }else{
            festival.setState(FestivalState.ANNOUNCED);
            festivalRepository.save(festival);
        }
    }
}
