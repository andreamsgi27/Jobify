package dev.andrea.jobify.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.andrea.jobify.DTO.ApplicationPhaseDTO;
import dev.andrea.jobify.model.Application;
import dev.andrea.jobify.model.ApplicationPhase;
import dev.andrea.jobify.model.Phase;
import dev.andrea.jobify.repository.ApplicationPhaseRepository;
import dev.andrea.jobify.repository.ApplicationRepository;
import dev.andrea.jobify.repository.PhaseRepository;

@Service
public class ApplicationPhaseService {

    private ApplicationPhaseRepository applicationPhaseRepository;
    private ApplicationRepository applicationRepository;
    private PhaseRepository phaseRepository;

    public ApplicationPhaseService(ApplicationPhaseRepository applicationPhaseRepository, ApplicationRepository applicationRepository, PhaseRepository phaseRepository) {
        this.applicationPhaseRepository = applicationPhaseRepository;
        this.applicationRepository = applicationRepository;
        this.phaseRepository = phaseRepository;
    }
    
    public void changePhase(Long applicationId, ApplicationPhaseDTO appPhaseDTO){
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + applicationId));

        Phase phase = phaseRepository.findById(appPhaseDTO.getPhase().getPhaseId())
            .orElseThrow(() -> new IllegalArgumentException("Phase not found with id: " + appPhaseDTO.getPhase().getPhaseId()));

        ApplicationPhase newPhase = new ApplicationPhase();
        newPhase.setApplication(application);
        newPhase.setPhase(phase);
        //Si el usuario quiere una fecha pasada o si la envía vacía: (sino se queda con la actual)
        if (appPhaseDTO.getDate() != null) {
            newPhase.setDate(appPhaseDTO.getDate());
        } else {
            newPhase.setDate(LocalDate.now());
        }
        applicationPhaseRepository.save(newPhase);
    }

    public ApplicationPhaseDTO getLastPhase(Long applicationId){
        Application application = applicationRepository.findById(applicationId)
        .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + applicationId));
        List<ApplicationPhaseDTO> phases = getAllPhases(applicationId);
        if(phases.isEmpty()){
            return null;
        }
            
        ApplicationPhaseDTO lastPhase = phases.get(phases.size() - 1);

        ApplicationPhaseDTO dto = new ApplicationPhaseDTO(); //Nuevo objeto DTO a devolver
        dto.setAppPhaseId(lastPhase.getAppPhaseId());
        dto.setPhase(lastPhase.getPhase());
        dto.setApplication(application);
        dto.setDate(lastPhase.getDate());

        return dto;
    }

    public List<ApplicationPhaseDTO> getAllPhases(Long applicationId){
        List<ApplicationPhase> phases = applicationPhaseRepository.findByApplicationId(applicationId);

        // Convertir las entidades ApplicationPhase en DTOs
        List<ApplicationPhaseDTO> phaseDTOs = new ArrayList<>();
        
        //Recorrer la lista, convirtiendo a DTO cada atributo de cada objeto de la lista
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
        List<ApplicationPhase> phases = applicationPhaseRepository.findByPhaseId(phaseId);
    
        // Convertir ApplicationPhase en DTOs, recorriendo cada atributo de cada objeto devuelto
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
