package dev.andrea.jobify.service;

import dev.andrea.jobify.model.JobType;
import dev.andrea.jobify.model.Phase;
import dev.andrea.jobify.repository.JobTypeRepository;
import dev.andrea.jobify.repository.PhaseRepository;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataInitializer {

    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Autowired
    private PhaseRepository phaseRepository;

    @PostConstruct
    public void init() {
        // Insertar JobTypes solo si no existen
        if (jobTypeRepository.count() == 0) {
            jobTypeRepository.save(new JobType("Remote"));
            jobTypeRepository.save(new JobType("Hybrid"));
            jobTypeRepository.save(new JobType("On-Site"));
        }

        // Insertar Phases solo si no existen
        if (phaseRepository.count() == 0) {
            phaseRepository.save(new Phase("Exploring"));
            phaseRepository.save(new Phase("Submitted"));
            phaseRepository.save(new Phase("Reviewing"));
            phaseRepository.save(new Phase("Interview"));
            phaseRepository.save(new Phase("Tech interview"));
            phaseRepository.save(new Phase("Final interview"));
            phaseRepository.save(new Phase("Offer"));
            phaseRepository.save(new Phase("Hired"));
        }
    }
}
