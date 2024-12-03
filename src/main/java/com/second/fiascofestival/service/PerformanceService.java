package com.second.fiascofestival.service;


import com.second.fiascofestival.model.Performance;
import com.second.fiascofestival.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    public Performance createPerformance(Performance performance) {
        if (performance.getName() == null || performance.getName().isEmpty()) {
            throw new IllegalArgumentException("Performance name is required.");
        }
        if (performance.getFestivalId() == null) {
            throw new IllegalArgumentException("Performance must belong to a festival.");
        }
        return performanceRepository.save(performance);
    }

    public Performance updatePerformance(String id, Performance updatedPerformance) {
        Performance existingPerformance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Performance not found."));
        existingPerformance.setDescription(updatedPerformance.getDescription());
        existingPerformance.setGenre(updatedPerformance.getGenre());
        existingPerformance.setDuration(updatedPerformance.getDuration());
        return performanceRepository.save(existingPerformance);
    }

    public List<Performance> getPerformancesByFestival(String festivalId) {
        return performanceRepository.findByFestivalId(festivalId);
    }

    public void deletePerformance(String id) {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Performance not found."));
        if (!"CREATED".equals(performance.getState())) {
            throw new IllegalStateException("Only performances in CREATED state can be deleted.");
        }
        performanceRepository.deleteById(id);
    }
    public List<Performance> searchPerformances(String genre, String festivalId) {
        return performanceRepository.findByCriteria(genre, festivalId);
    }
    public Performance submitPerformance(String id) {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Performance not found"));
        performance.setState("SUBMITTED");
        return performanceRepository.save(performance);
    }




}
