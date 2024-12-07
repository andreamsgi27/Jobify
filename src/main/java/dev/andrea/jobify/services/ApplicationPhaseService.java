package dev.andrea.jobify.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.andrea.jobify.DTOs.ApplicationPhaseDTO;
import dev.andrea.jobify.DTOs.PhaseDTO;
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
    private UserRepository userRepository; // Repositorio para obtener el usuario autenticado
    
    public void changePhase(Long applicationId, ApplicationPhaseDTO appPhaseDTO, PhaseDTO phaseDTO) {
        // Obtener el usuario autenticado
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + applicationId));

        // Verificar que el usuario autenticado es el propietario de la aplicación
        if (!application.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized: You can only modify your own applications");
        }

        Long phaseId = phaseDTO.getPhaseId();
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
        // Obtener el usuario autenticado
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Application application = applicationRepository.findById(applicationId)
        .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + applicationId));

        // Verificar que el usuario autenticado es el propietario de la aplicación
        if (!application.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized: You can only view your own applications");
        }

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
        // Obtener el usuario autenticado
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Application application = applicationRepository.findById(applicationId)
        .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + applicationId));

        // Verificar que el usuario autenticado es el propietario de la aplicación
        if (!application.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized: You can only view your own applications");
        }

        List<ApplicationPhase> phases = applicationPhaseRepository.findByApplication_applicationId(applicationId);

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
        List<ApplicationPhase> phases = applicationPhaseRepository.findByPhase_PhaseId(phaseId);
    
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