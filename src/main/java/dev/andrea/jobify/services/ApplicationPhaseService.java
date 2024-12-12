package dev.andrea.jobify.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.andrea.jobify.DTOs.ApplicationPhaseDTO;
import dev.andrea.jobify.models.Application;
import dev.andrea.jobify.models.ApplicationPhase;
import dev.andrea.jobify.models.Phase;
import dev.andrea.jobify.models.User;
import dev.andrea.jobify.repositories.ApplicationPhaseRepository;
import dev.andrea.jobify.repositories.ApplicationRepository;
import dev.andrea.jobify.repositories.PhaseRepository;
import dev.andrea.jobify.repositories.UserRepository;

@Service
public class ApplicationPhaseService {

    @Autowired
    private ApplicationPhaseRepository applicationPhaseRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private PhaseRepository phaseRepository;
    @Autowired
    private UserRepository userRepository;
    
    public void changePhase(Long applicationId, ApplicationPhaseDTO appPhaseDTO) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userRepository.existsByUsername(authenticatedUsername)) {
            throw new RuntimeException("Authenticated user not found");
        }

        User user = userRepository.findByUsername(authenticatedUsername)
            .orElseThrow(() -> new RuntimeException("Authenticated user details not found"));

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + applicationId));

        if (!application.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized: You can only modify your own applications");
        }

        if (appPhaseDTO == null || appPhaseDTO.getPhase() == null) {
            throw new IllegalArgumentException("Phase details must be provided");
        }
        Long phaseId = appPhaseDTO.getPhase().getPhaseId();
        
        Phase phase = phaseRepository.findById(phaseId)
            .orElseThrow(() -> new IllegalArgumentException("Phase not found with id: " + phaseId));

        ApplicationPhase newPhase = new ApplicationPhase();
        newPhase.setApplication(application);
        newPhase.setPhase(phase);

        if (appPhaseDTO.getDate() != null) {
            newPhase.setDate(appPhaseDTO.getDate());
        } else {
            newPhase.setDate(LocalDate.now());
        }

        applicationPhaseRepository.save(newPhase);
    }

    public ApplicationPhaseDTO getLastPhase(Long applicationId){
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userRepository.existsByUsername(authenticatedUsername)) {
            throw new RuntimeException("Authenticated user not found");
        }

        User user = userRepository.findByUsername(authenticatedUsername)
            .orElseThrow(() -> new RuntimeException("Authenticated user details not found"));

        Application application = applicationRepository.findById(applicationId)
        .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + applicationId));

        if (!application.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized: You can only view your own applications");
        }

        List<ApplicationPhaseDTO> phases = getAllPhases(applicationId);
        if(phases.isEmpty()){
            return null;
        }
            
        ApplicationPhaseDTO lastPhase = phases.get(phases.size() - 1);

        ApplicationPhaseDTO dto = new ApplicationPhaseDTO();
        dto.setAppPhaseId(lastPhase.getAppPhaseId());
        dto.setPhase(lastPhase.getPhase());
        dto.setApplication(application);
        dto.setDate(lastPhase.getDate());

        return dto;
    }

    public List<ApplicationPhaseDTO> getAllPhases(Long applicationId){
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userRepository.existsByUsername(authenticatedUsername)) {
            throw new RuntimeException("Authenticated user not found");
        }

        User user = userRepository.findByUsername(authenticatedUsername)
            .orElseThrow(() -> new RuntimeException("Authenticated user details not found"));

        Application application = applicationRepository.findById(applicationId)
        .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + applicationId));

        if (!application.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized: You can only view your own applications");
        }

        List<ApplicationPhase> phases = applicationPhaseRepository.findByApplication_applicationId(applicationId);

        List<ApplicationPhaseDTO> phaseDTOs = new ArrayList<>();
        
        for (ApplicationPhase phase : phases) {
            ApplicationPhaseDTO dto = new ApplicationPhaseDTO();
            dto.setAppPhaseId(phase.getAppPhaseId());
            dto.setPhase(phase.getPhase());
            dto.setApplication(phase.getApplication());
            dto.setDate(phase.getDate());
            phaseDTOs.add(dto);
        }
        
        return phaseDTOs;
    }

    public List<ApplicationPhaseDTO> getApplicationsByPhase(Long phaseId){
        List<ApplicationPhase> phases = applicationPhaseRepository.findByPhase_PhaseId(phaseId);
    
        List<ApplicationPhaseDTO> phaseDTOs = new ArrayList<>();
        
        for (ApplicationPhase phase : phases) {
            ApplicationPhaseDTO dto = new ApplicationPhaseDTO();
            dto.setAppPhaseId(phase.getAppPhaseId());
            dto.setPhase(phase.getPhase());
            dto.setApplication(phase.getApplication());
            dto.setDate(phase.getDate());
            phaseDTOs.add(dto);
        }

        return phaseDTOs;
    }
}