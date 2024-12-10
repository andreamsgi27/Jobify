package dev.andrea.jobify.controllers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;

import dev.andrea.jobify.DTOs.ApplicationDTO;
import dev.andrea.jobify.models.Application;
import dev.andrea.jobify.models.JobType;
import dev.andrea.jobify.models.User;
import dev.andrea.jobify.services.ApplicationService;

@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationController applicationController;

    private ApplicationDTO applicationDTO;

    @BeforeEach
    void setUp() {
        // Crear objetos relacionados
        User user = new User(1L, "John Doe", "password123", "johndoe@example.com");
        JobType jobType = new JobType(1L, "Full-Time");
        Application application = new Application(
                1L,
                user,
                "Tech Corp",
                "Software Developer",
                "Gijon",
                "Java, Spring Boot",
                jobType,
                70000,
                "https://techcorp.com/jobs/123",
                "Exciting opportunity"
        );

        // Inicializar ApplicationDTO usando Application
        applicationDTO = new ApplicationDTO(application);
    }

    @Test
    void createApp_shouldReturnCreatedApplication() {
        when(applicationService.createApp(any(ApplicationDTO.class))).thenReturn(applicationDTO);

        ResponseEntity<ApplicationDTO> response = applicationController.createApp(applicationDTO);

        assertEquals(CREATED, response.getStatusCode());
        assertEquals(applicationDTO, response.getBody());
    }

    @Test
    void updateApp_shouldReturnUpdatedApplication() {
        // Crear los datos actualizados simulados
        User user = new User(1L, "John Doe", "password123", "johndoe@example.com");
        JobType jobType = new JobType(1L, "Full-Time");
        Application updatedApplication = new Application(
                1L,
                user,
                "Tech Corp",
                "Software Engineer",
                "Remote",
                "Advanced Java, Microservices",
                jobType,
                85000,
                "https://techcorp.com/jobs/123",
                "Updated application notes"
        );
        ApplicationDTO updatedApplicationDTO = new ApplicationDTO(updatedApplication);

        // Mock del servicio para devolver los datos actualizados
        when(applicationService.updateApp(eq(applicationDTO), eq(1L))).thenReturn(updatedApplicationDTO);

        // Llamada al controlador
        ResponseEntity<ApplicationDTO> response = applicationController.updateApp(1L, applicationDTO);

        // Verificaciones
        assertEquals(OK, response.getStatusCode());
        assertEquals(updatedApplicationDTO, response.getBody());
    }

    @Test
    void deleteApp_shouldReturnNoContent() {
        doNothing().when(applicationService).deleteApp(1L);

        ResponseEntity<Void> response = applicationController.deleteApp(1L);

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(applicationService, times(1)).deleteApp(1L);
    }

    @Test
    void listAppsByUser_shouldReturnListOfApplications() {
        // Crear datos simulados
        User user = new User(1L, "John Doe", "password123", "johndoe@example.com");
        JobType jobType = new JobType(1L, "Full-Time");

        Application application1 = new Application(
                1L,
                user,
                "Tech Corp",
                "Software Developer",
                "Remote",
                "Java, Spring Boot",
                jobType,
                80000,
                "https://techcorp.com/jobs/123",
                "Application notes 1"
        );
        Application application2 = new Application(
                2L,
                user,
                "Data Analytics Inc.",
                "Data Scientist",
                "On-site",
                "Python, Machine Learning",
                jobType,
                95000,
                "https://dataanalytics.com/jobs/456",
                "Application notes 2"
        );

        List<ApplicationDTO> applications = Arrays.asList(
                new ApplicationDTO(application1),
                new ApplicationDTO(application2)
        );

        // Mock del servicio para devolver la lista de aplicaciones
        when(applicationService.listAppsByUser()).thenReturn(applications);

        // Llamada al controlador
        ResponseEntity<List<ApplicationDTO>> response = applicationController.listAppsByUser();

        // Verificaciones
        assertEquals(OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Software Developer", response.getBody().get(0).getPosition());
        assertEquals("Data Scientist", response.getBody().get(1).getPosition());
    }



    @Test
    void getAppById_shouldReturnApplication() {
        when(applicationService.getAppById(1L)).thenReturn(applicationDTO);

        ResponseEntity<ApplicationDTO> response = applicationController.getAppById(1L);

        assertEquals(OK, response.getStatusCode());
        assertEquals(applicationDTO, response.getBody());
    }
}