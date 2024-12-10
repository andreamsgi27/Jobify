package dev.andrea.jobify.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import dev.andrea.jobify.DTOs.ApplicationPhaseDTO;
import dev.andrea.jobify.services.ApplicationPhaseService;
import dev.andrea.jobify.models.ApplicationPhase;
import dev.andrea.jobify.models.Phase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ApplicationPhaseController.class)
public class ApplicationPhaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ApplicationPhaseService applicationPhaseService;

    @InjectMocks
    private ApplicationPhaseController applicationPhaseController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(applicationPhaseController).build();
    }

    @Test
    void changePhase_shouldReturnSuccessMessage() throws Exception {
        ApplicationPhaseDTO appPhaseDTO = new ApplicationPhaseDTO(1L, new Phase(1L, "Interview"), null, null);
        
        // Mock the service method
        when(applicationPhaseService.changePhase(any(Long.class), any(ApplicationPhaseDTO.class)))
                .thenReturn("Phase updated successfully");

        mockMvc.perform(post("/app/phase/{applicationId}/change-phase", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"phase\": {\"phaseId\": 1, \"name\": \"Interview\"} }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Phase updated successfully"));
    }

    private OngoingStubbing<ApplicationPhaseDTO> when(Object changePhase) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'when'");
    }

    @Test
    void getLastPhase_shouldReturnLastPhase() throws Exception {
        ApplicationPhaseDTO appPhaseDTO = new ApplicationPhaseDTO(1L, new Phase(1L, "Interview"), null, null);
        
        // Mock the service method
        when(applicationPhaseService.getLastPhase(1L)).thenReturn(appPhaseDTO);

        mockMvc.perform(get("/app/phase/{applicationId}/last", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phase.name").value("Interview"))
                .andExpect(jsonPath("$.appPhaseId").value(1L));
    }

    @Test
    void getAllPhases_shouldReturnAllPhases() throws Exception {
        ApplicationPhaseDTO phase1 = new ApplicationPhaseDTO(1L, new Phase(1L, "Interview"), null, null);
        ApplicationPhaseDTO phase2 = new ApplicationPhaseDTO(2L, new Phase(2L, "Offer"), null, null);
        List<ApplicationPhaseDTO> phases = Arrays.asList(phase1, phase2);

        // Mock the service method
        when(applicationPhaseService.getAllPhases(1L)).thenReturn(phases);

        mockMvc.perform(get("/app/phase/{applicationId}/all", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].phase.name").value("Interview"))
                .andExpect(jsonPath("$[1].phase.name").value("Offer"));
    }

    @Test
    void getApplicationsByPhase_shouldReturnApplications() throws Exception {
        ApplicationPhaseDTO appPhaseDTO = new ApplicationPhaseDTO(1L, new Phase(1L, "Interview"), null, null);
        List<ApplicationPhaseDTO> applications = Arrays.asList(appPhaseDTO);

        // Mock the service method
        when(applicationPhaseService.getApplicationsByPhase(1L)).thenReturn(applications);

        mockMvc.perform(get("/app/phase/{phaseId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].phase.name").value("Interview"));
    }
}