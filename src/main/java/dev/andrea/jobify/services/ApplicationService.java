package dev.andrea.jobify.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.andrea.jobify.DTOs.ApplicationDTO;
import dev.andrea.jobify.models.Application;
import dev.andrea.jobify.models.JobType;
import dev.andrea.jobify.models.User;
import dev.andrea.jobify.repositories.ApplicationRepository;
import dev.andrea.jobify.repositories.JobTypeRepository;
import dev.andrea.jobify.repositories.UserRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Autowired
    private UserRepository userRepository;

    
    public ApplicationDTO createApp(ApplicationDTO applicationDTO) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userRepository.existsByUsername(authenticatedUsername)) {
            throw new RuntimeException("Authenticated user not found");
        }

        User user = userRepository.findByUsername(authenticatedUsername)
            .orElseThrow(() -> new RuntimeException("Authenticated user details not found"));

        JobType jobType = jobTypeRepository.findById(applicationDTO.getJobTypeId())
            .orElseThrow(() -> new RuntimeException("JobType not found with id " + applicationDTO.getJobTypeId()));

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
    
            String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

            if (!userRepository.existsByUsername(authenticatedUsername)) {
                throw new RuntimeException("Authenticated user not found");
            }
    
            User user = userRepository.findByUsername(authenticatedUsername)
                .orElseThrow(() -> new RuntimeException("Authenticated user details not found"));
    
            if (!existingApplication.getUser().getUserId().equals(user.getUserId())) {
                throw new RuntimeException("Unauthorized: You can only update your own applications");
            }
        
        JobType jobType = jobTypeRepository.findById(applicationDTO.getJobTypeId())
            .orElseThrow(() -> new RuntimeException("JobType not found by id " + applicationDTO.getJobTypeId()));
    
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
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userRepository.existsByUsername(authenticatedUsername)) {
            throw new RuntimeException("Authenticated user not found");
        }

        User user = userRepository.findByUsername(authenticatedUsername)
            .orElseThrow(() -> new RuntimeException("Authenticated user details not found"));

        Application existingApplication = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
    
        if (!existingApplication.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized: You can only delete your own applications");
        }
        applicationRepository.delete(existingApplication);
    }

    public List<ApplicationDTO> listAppsByUser() {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userRepository.existsByUsername(authenticatedUsername)) {
            throw new RuntimeException("Authenticated user not found");
        }

        User user = userRepository.findByUsername(authenticatedUsername)
            .orElseThrow(() -> new RuntimeException("Authenticated user details not found"));

        List<Application> applications = applicationRepository.findByUser_UserId(user.getUserId());
        List<ApplicationDTO> applicationDTOs = new ArrayList<>();

        for (Application application : applications) {
            applicationDTOs.add(new ApplicationDTO(application));
        }

        return applicationDTOs;
    }


    public ApplicationDTO getAppById(Long applicationId) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userRepository.existsByUsername(authenticatedUsername)) {
            throw new RuntimeException("Authenticated user not found");
        }

        User user = userRepository.findByUsername(authenticatedUsername)
            .orElseThrow(() -> new RuntimeException("Authenticated user details not found"));

        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));

        if (!application.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized: You can only view your own applications");
        }

        return new ApplicationDTO(application);
    }

    public List<ApplicationDTO> getAppByKeyword(String keyword) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userRepository.existsByUsername(authenticatedUsername)) {
            throw new RuntimeException("Authenticated user not found");
        }
        User user = userRepository.findByUsername(authenticatedUsername)
            .orElseThrow(() -> new RuntimeException("Authenticated user details not found"));

        List<Application> applications = applicationRepository.findByKeywordAndUserId(keyword, user.getUserId());

        List<ApplicationDTO> applicationDTOs = applications.stream()
            .map(ApplicationDTO::new)
            .collect(Collectors.toList());

        return applicationDTOs;
    }

    public int countApplicationsByUserId(Long userId) {
        return applicationRepository.countApplicationsByUserId(userId);
    }

    public int getUniqueCompaniesCount() {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userRepository.existsByUsername(authenticatedUsername)) {
            throw new RuntimeException("Authenticated user not found");
        }
        User user = userRepository.findByUsername(authenticatedUsername)
            .orElseThrow(() -> new RuntimeException("Authenticated user details not found"));
        
            List<String> companies = applicationRepository.findDistinctCompaniesByUserId(user.getUserId());
        return companies.size();
    }

    public int getTotalApplicationsCount() {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    
        if (!userRepository.existsByUsername(authenticatedUsername)) {
            throw new RuntimeException("Authenticated user not found");
        }
    
        User user = userRepository.findByUsername(authenticatedUsername)
            .orElseThrow(() -> new RuntimeException("Authenticated user details not found"));
        
        int totalCount = applicationRepository.countByUser_UserId(user.getUserId());
        return (int) totalCount;
    }
}
