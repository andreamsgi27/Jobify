package dev.andrea.jobify.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import dev.andrea.jobify.DTO.ApplicationDTO;
import dev.andrea.jobify.model.Application;
import dev.andrea.jobify.model.JobType;
import dev.andrea.jobify.model.User;
import dev.andrea.jobify.repository.ApplicationRepository;
import dev.andrea.jobify.repository.JobTypeRepository;
import dev.andrea.jobify.repository.UserRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PhaseService phaseService;

    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Autowired
    private UserRepository userRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    
    public ApplicationDTO createApp(ApplicationDTO applicationDTO) {
        User user = userRepository.findById(applicationDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with id " + applicationDTO.getUserId()));
    
        JobType jobType = jobTypeRepository.findById(applicationDTO.getJobTypeId())
            .orElseThrow(() -> new RuntimeException("JobType not found by id " + applicationDTO.getJobTypeId()));
    
        Application application = new Application(
            applicationDTO.getAppId(),
            user,
            applicationDTO.getCompany(),
            applicationDTO.getPosition(),
            applicationDTO.getLocation(),
            applicationDTO.getRequirements(),
            jobType,
            applicationDTO.getSalary(),
            applicationDTO.getLink(),
            applicationDTO.getNotes()
        );
    
        Application savedApplication = applicationRepository.save(application);
        return new ApplicationDTO(savedApplication);
    }
    
    

    public ApplicationDTO updateApp(ApplicationDTO applicationDTO, Long applicationId) {
        Application existingApplication = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
    
        User user = userRepository.findById(applicationDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with id " + applicationDTO.getUserId()));
    
        JobType jobType = jobTypeRepository.findById(applicationDTO.getJobTypeId())
            .orElseThrow(() -> new RuntimeException("JobType not found by id " + applicationDTO.getJobTypeId()));
    
        // Actualizamos los campos de la aplicación existente
        existingApplication.setUser(user);
        existingApplication.setCompany(applicationDTO.getCompany());
        existingApplication.setPosition(applicationDTO.getPosition());
        existingApplication.setLocation(applicationDTO.getLocation());
        existingApplication.setRequirements(applicationDTO.getRequirements());
        existingApplication.setJobType(jobType);
        existingApplication.setSalary(applicationDTO.getSalary());
        existingApplication.setLink(applicationDTO.getLink());
        existingApplication.setNotes(applicationDTO.getNotes());
    
        Application updatedApplication = applicationRepository.save(existingApplication);

        return new ApplicationDTO(updatedApplication);
    }
    

    public void deleteApp(Long applicationId) {
        Application existingApplication = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
        applicationRepository.delete(existingApplication);
    }

    public List<ApplicationDTO> listAppsByUser(Long userId) {
    List<Application> applications = applicationRepository.findByUserId(userId);
    List<ApplicationDTO> applicationDTOs = new ArrayList<>();
    
    for (Application application : applications) {
        // Convertir Application a ApplicationDTO
        applicationDTOs.add(new ApplicationDTO(application));
    }
    
    return applicationDTOs;
}


    public Application getAppById(Long applicationId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppById'");
    }

    public List<Application> getAppByKeyword(String keyword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppByKeyword'");
    }

    /*no esta aqui
    public Application changeApplicationPhase(Long applicationId, Long newPhaseId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Phase newPhase = phaseService.getPhaseById(newPhaseId);
        application.changePhase(newPhase);

        return applicationRepository.save(application);
    } */
    
}
