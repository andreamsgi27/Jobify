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

//Endpoints para consultar las fases de una candidatura específica junto con la fecha de cambio.
//Endpoint para registrar un nuevo cambio de fase con la fecha correspondiente.

@RestController
@RequestMapping("/app/phase")
public class ApplicationPhaseController {
    
    private final ApplicationPhaseService applicationPhaseService;

    public ApplicationPhaseController(ApplicationPhaseService applicationPhaseService) {
        this.applicationPhaseService = applicationPhaseService;
    }

    //Cambia la fase de la candidatura
    @PostMapping("/{applicationId}/change-phase")
    public ResponseEntity<String> changePhase(@PathVariable Long applicationId, @RequestBody ApplicationPhaseDTO appPhaseDTO) {
        applicationPhaseService.changePhase(applicationId, appPhaseDTO); // Envia DTO al servicio
        return ResponseEntity.ok("Phase updated successfully");
    }

    //devuelve la última fase(name) con la fecha del cambio
    @GetMapping("/{applicationId}/last")
    public ResponseEntity<ApplicationPhaseDTO> getLastPhase(@PathVariable Long applicationId) {
        ApplicationPhaseDTO lastPhase = applicationPhaseService.getLastPhase(applicationId); // Usamos el DTO
        return ResponseEntity.ok(lastPhase);
    }

    //devuelve las fases(nombre) con sus fechas de cambio de una misma candidatura
    @GetMapping("/{applicationId}/all")
    public ResponseEntity<List<ApplicationPhaseDTO>> getAllPhases(@PathVariable Long applicationId) {
        List<ApplicationPhaseDTO> phases = applicationPhaseService.getAllPhases(applicationId); // Usamos el DTO
        return ResponseEntity.ok(phases);
    }

    //Estadística/filtro: devuelve todas las candidaturas que se encuentran en la misma fase
    @GetMapping("/{phaseId}")
    public ResponseEntity<List<ApplicationPhaseDTO>> getApplicationsByPhase(@PathVariable Long phaseId) {
        List<ApplicationPhaseDTO> applications = applicationPhaseService.getApplicationsByPhase(phaseId); // Usamos el DTO
        return ResponseEntity.ok(applications);
    }

    
    

}
