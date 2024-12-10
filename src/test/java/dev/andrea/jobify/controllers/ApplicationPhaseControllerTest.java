package dev.andrea.jobify.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import dev.andrea.jobify.DTOs.ApplicationPhaseDTO;
import dev.andrea.jobify.models.Application;
import dev.andrea.jobify.models.JobType;
import dev.andrea.jobify.models.Phase;
import dev.andrea.jobify.models.User;
import dev.andrea.jobify.services.ApplicationPhaseService;

@ExtendWith(MockitoExtension.class)
public class ApplicationPhaseControllerTest {

    @Mock
    private ApplicationPhaseService applicationPhaseService;

    @InjectMocks
    private ApplicationPhaseController applicationPhaseController;

    private ApplicationPhaseDTO appPhaseDTO;

    @BeforeEach
    void setUp() {
        appPhaseDTO = new ApplicationPhaseDTO(
            1L,
            new Phase(1L, "Exploring"),
            new Application(1L,
                new User(1L, "John Doe", "password123", "johndoe@example.com"), 
                "Tech Corp", "Software Developer", "Gijon", "Java, Spring Boot", 
                new JobType(1L, "Full-Time"), 70000, "https://techcorp.com/jobs/123", 
                "Exciting opportunity"),
            LocalDate.parse("2022-01-01")
        );
    }

    @Test
    void changePhase_ShouldReturnOk() throws Exception {
        // Mockear la llamada al servicio
        doNothing().when(applicationPhaseService).changePhase(any(Long.class), any(ApplicationPhaseDTO.class));

        // Simular la petición al endpoint
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(applicationPhaseController).build();

        mockMvc.perform(post("/app/phase/1/change-phase")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"appPhaseId\": 1, \"phase\": { \"phaseId\": 1, \"name\": \"Exploring\" }, "
                        + "\"application\": { \"applicationId\": 1 }, \"date\": \"2022-01-01\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Phase updated successfully"));
    }

    @Test
    void getLastPhase_ShouldReturnLastPhase() {
        when(applicationPhaseService.getLastPhase(eq(1L))).thenReturn(appPhaseDTO);

        ResponseEntity<ApplicationPhaseDTO> response = applicationPhaseController.getLastPhase(1L);

        assertEquals(OK, response.getStatusCode());
        assertEquals(appPhaseDTO, response.getBody());
    }

    @Test
    void getAllPhases_ShouldReturnAllPhases() {
        // Crear lista de fases simuladas
        List<ApplicationPhaseDTO> phases = Arrays.asList(appPhaseDTO);

        when(applicationPhaseService.getAllPhases(eq(1L))).thenReturn(phases);

        ResponseEntity<List<ApplicationPhaseDTO>> response = applicationPhaseController.getAllPhases(1L);

        assertEquals(OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Exploring", response.getBody().get(0).getPhase().getName());
    }

    @Test
    void getApplicationsByPhase_ShouldReturnApplicationsInPhase() {
        // Crear lista de fases simuladas
        List<ApplicationPhaseDTO> phases = Arrays.asList(appPhaseDTO);
    
        // Mockear el servicio para devolver las fases simuladas
        when(applicationPhaseService.getApplicationsByPhase(eq(1L))).thenReturn(phases);
    
        // Simular la llamada al controlador
        ResponseEntity<List<ApplicationPhaseDTO>> response = applicationPhaseController.getApplicationsByPhase(1L);
    
        // Verificar que el código de estado es 200 OK
        assertEquals(OK, response.getStatusCode());
    
        // Verificar que el tamaño de la lista es 1
        assertEquals(1, response.getBody().size());
    
        // Verificar que el nombre de la fase sea "Exploring"
        assertEquals("Exploring", response.getBody().get(0).getPhase().getName());
    }


}
