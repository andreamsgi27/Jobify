package dev.andrea.jobify.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.andrea.jobify.DTOs.ApplicationPhaseDTO;
import dev.andrea.jobify.services.ApplicationPhaseService;

@RestController
@RequestMapping("/app/phase")
public class ApplicationPhaseController {
    
    private final ApplicationPhaseService applicationPhaseService;

    public ApplicationPhaseController(ApplicationPhaseService applicationPhaseService) {
        this.applicationPhaseService = applicationPhaseService;
    }

    @PostMapping("/{applicationId}/change-phase")
    public ResponseEntity<String> changePhase(@PathVariable Long applicationId, @RequestBody ApplicationPhaseDTO appPhaseDTO) {
        applicationPhaseService.changePhase(applicationId, appPhaseDTO);
        return ResponseEntity.ok("Phase updated successfully");
    }

    @GetMapping("/{applicationId}/last")
    public ResponseEntity<ApplicationPhaseDTO> getLastPhase(@PathVariable Long applicationId) {
        ApplicationPhaseDTO lastPhase = applicationPhaseService.getLastPhase(applicationId);
        return ResponseEntity.ok(lastPhase);
    }

    @GetMapping("/{applicationId}/all")
    public ResponseEntity<List<ApplicationPhaseDTO>> getAllPhases(@PathVariable Long applicationId) {
        List<ApplicationPhaseDTO> phases = applicationPhaseService.getAllPhases(applicationId);
        return ResponseEntity.ok(phases);
    }

    @GetMapping("/{phaseId}")
    public ResponseEntity<List<ApplicationPhaseDTO>> getApplicationsByPhase(@PathVariable Long phaseId) {
        List<ApplicationPhaseDTO> applications = applicationPhaseService.getApplicationsByPhase(phaseId);
        return ResponseEntity.ok(applications);
    }

    
    

}
