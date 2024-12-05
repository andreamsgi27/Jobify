package dev.andrea.jobify.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.andrea.jobify.DTO.ApplicationPhaseDTO;

@Service
public class ApplicationPhaseService {
    
    public String changePhase(Long applicationId, ApplicationPhaseDTO appPhaseDTO){
        return "Application phase changed";
    }

    public ApplicationPhaseDTO getLastPhase(Long applicationId){
        return new ApplicationPhaseDTO();
    }

    public List<ApplicationPhaseDTO> getAllPhases(Long applicationId){
        return new ArrayList<>();
    }

    public List<ApplicationPhaseDTO> getApplicationsByPhase(Long phaseId){
        return new ArrayList<>();
    }
}
