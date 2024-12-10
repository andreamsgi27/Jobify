package dev.andrea.jobify.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.andrea.jobify.DTOs.ApplicationDTO;
import dev.andrea.jobify.services.ApplicationService;

@RestController
@RequestMapping("/application")
public class ApplicationController {
    
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<ApplicationDTO> createApp(@RequestBody ApplicationDTO applicationDTO) {
        ApplicationDTO createdApp = applicationService.createApp(applicationDTO);
        return new ResponseEntity<>(createdApp, HttpStatus.CREATED);
    }

    @PutMapping("/{applicationId}")
    public ResponseEntity<ApplicationDTO> updateApp(@PathVariable Long applicationId, @RequestBody ApplicationDTO applicationDTO) {
        ApplicationDTO updatedApp = applicationService.updateApp(applicationDTO, applicationId);
        return ResponseEntity.ok(updatedApp);
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApp(@PathVariable Long applicationId) {
        applicationService.deleteApp(applicationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> listAppsByUser() {
        List<ApplicationDTO> applications = applicationService.listAppsByUser();
        return ResponseEntity.ok(applications);
    }

    @GetMapping(path = "/{applicationId}")
    public ResponseEntity<ApplicationDTO> getAppById(@PathVariable Long applicationId) {
        ApplicationDTO application = applicationService.getAppById(applicationId);
        return ResponseEntity.ok(application);
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<ApplicationDTO>> getAppByKeyword(@PathVariable String keyword) {
        List<ApplicationDTO> applications = applicationService.getAppByKeyword(keyword);
        return ResponseEntity.ok(applications);
    }
}
