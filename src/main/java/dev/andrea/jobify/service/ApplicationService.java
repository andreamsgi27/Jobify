package dev.andrea.jobify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.andrea.jobify.model.Application;
import dev.andrea.jobify.model.Phase;
import dev.andrea.jobify.repository.ApplicationRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PhaseService phaseService;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    
    public Application createApp(Application application) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createApp'");
    }

    public Application updateApp(Long applicationId, Application application) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateApp'");
    }

    public void deleteApp(Long applicationId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteApp'");
    }

    public List<Application> listApps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listApps'");
    }

    public Application getAppById(Long applicationId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppById'");
    }

    public List<Application> getAppByKeyword(String keyword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppByKeyword'");
    }

    public Application changeApplicationPhase(Long applicationId, Long newPhaseId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Phase newPhase = phaseService.getPhaseById(newPhaseId);
        application.changePhase(newPhase);

        return applicationRepository.save(application);
    }
    
}
