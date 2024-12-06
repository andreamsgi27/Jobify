package dev.andrea.jobify.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private JobTypeRepository jobTypeRepository;

    @Autowired
    private UserRepository userRepository;

    
    public ApplicationDTO createApp(ApplicationDTO applicationDTO) {
    // Obtener usuario autenticado y validar datos de Application
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        JobType jobType = jobTypeRepository.findById(applicationDTO.getJobTypeId())
            .orElseThrow(() -> new RuntimeException("JobType not found with id " + applicationDTO.getJobTypeId()));

        // Crear la candidatura
        Application application = new Application(
            null,
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
    
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    
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
        // Obtener el email del usuario autenticado
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    
        // Verificar que la aplicación exista
        Application existingApplication = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
    
        // Verificar que el usuario autenticado sea el propietario de la aplicación
        if (!existingApplication.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized: You can only delete your own applications");
        }
    
        // Eliminar la aplicación
        applicationRepository.delete(existingApplication);
    }

    public List<ApplicationDTO> listAppsByUser() {
        // Obtener el email del usuario autenticado
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    
        // Obtener las aplicaciones asociadas al usuario autenticado
        List<Application> applications = applicationRepository.findByUserId(user.getUserId());
        List<ApplicationDTO> applicationDTOs = new ArrayList<>();
        
        // Convertir las entidades Application a DTOs
        for (Application application : applications) {
            applicationDTOs.add(new ApplicationDTO(application));
        }
        
        return applicationDTOs;
    }


    public ApplicationDTO getAppById(Long applicationId) {
        // Obtener el email del usuario autenticado
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    
        // Buscar la aplicación por su ID
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
    
        // Verificar que la aplicación pertenece al usuario autenticado
        if (!application.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized: You can only view your own applications");
        }
    
        // Convertir la entidad Application a un DTO y devolverla
        return new ApplicationDTO(application);
    }

    public List<ApplicationDTO> getAppByKeyword(String keyword) {
        // Obtener el email del usuario autenticado
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    
        // Buscar aplicaciones por palabra clave y propiedad del usuario
        List<Application> applications = applicationRepository.findByKeywordAndUserId(keyword, user.getUserId());
    
        // Convertir las aplicaciones encontradas a DTOs
        List<ApplicationDTO> applicationDTOs = new ArrayList<>();
        for (Application application : applications) {
            applicationDTOs.add(new ApplicationDTO(application));
        }
    
        return applicationDTOs;
    }
}
