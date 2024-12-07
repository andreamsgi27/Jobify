package dev.andrea.jobify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.andrea.jobify.models.Phase;
import dev.andrea.jobify.repositories.PhaseRepository;

@Service
public class PhaseService {
    
    @Autowired
    private PhaseRepository phaseRepository;

    public Phase getPhaseById(Long phaseId) {
        return phaseRepository.findById(phaseId)
                .orElseThrow(() -> new RuntimeException("Phase not found"));
    }
    
}
