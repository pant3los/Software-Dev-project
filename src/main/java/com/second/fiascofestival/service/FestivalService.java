package com.second.fiascofestival.service;

import com.second.fiascofestival.model.Festival;
import com.second.fiascofestival.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;

    public Festival createFestival(Festival festival) {
        if (festival.getName() == null || festival.getName().isEmpty()) {
            throw new IllegalArgumentException("Festival name is required.");
        }
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
}
