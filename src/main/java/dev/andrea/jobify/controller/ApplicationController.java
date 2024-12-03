package dev.andrea.jobify.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.andrea.jobify.DTO.ApplicationDTO;
import dev.andrea.jobify.model.Application;
import dev.andrea.jobify.service.ApplicationService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/application")
public class ApplicationController {
    
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping (path= "/create")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application createdApp = applicationService.createApp(application);
        return new ResponseEntity<>(createdApp, HttpStatus.CREATED);
    }

    @PutMapping("/{applicationId}")
    public ResponseEntity<Application> updateApp(@PathVariable Long applicationId, @RequestBody Application application) {
        Application updatedApp = applicationService.updateApp(applicationId, application);
            return ResponseEntity.ok(updatedApp);
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApp(@PathVariable Long applicationId) {
            applicationService.deleteApp(applicationId);
            return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<List<Application>> listApps() {
        List<Application> applications = applicationService.listApps();
        return ResponseEntity.ok(applications);
    }

    @GetMapping(path = "/{applicationId}")
    public ResponseEntity<Application> getAppById(@PathVariable Long applicationId) {
        Application application = applicationService.getAppById(applicationId);
        return ResponseEntity.ok(application);
    }

    //Cuadro de búsqueda
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<Application>> getAppByKeyword(@PathVariable String keyword) {
        List<Application> applications = applicationService.getAppByKeyword(keyword);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/{applicationId}/changePhase/{newPhaseId}")
    public ResponseEntity<ApplicationDTO> changePhase(
            @PathVariable Long applicationId,
            @PathVariable Long newPhaseId) {
        Application updatedApplication = applicationService.changeApplicationPhase(applicationId, newPhaseId);
        ApplicationDTO applicationDTO = new ApplicationDTO(updatedApplication);
        return ResponseEntity.ok(applicationDTO);
    }

/*
    //Filtros
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Application>> getAppByLocation(@PathVariable String location) {
        List<Application> applications = applicationService.getAppByLocation();
        return ResponseEntity.ok(applications);
    } */

}