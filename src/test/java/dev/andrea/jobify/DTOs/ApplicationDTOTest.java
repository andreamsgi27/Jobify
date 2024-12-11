package dev.andrea.jobify.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import dev.andrea.jobify.models.Application;
import dev.andrea.jobify.models.JobType;
import dev.andrea.jobify.models.User;

class ApplicationDTOTest {

    @Test
    void constructorShouldMapFieldsCorrectly() {
        // Arrange: Crear objetos mockeados de User y JobType para simular la Application
        User user = mock(User.class);
        JobType jobType = mock(JobType.class);
        Application application = mock(Application.class);
        
        // Definir comportamientos de los mocks
        when(user.getUserId()).thenReturn(1L);
        when(jobType.getJobTypeId()).thenReturn(2L);
        when(application.getApplicationId()).thenReturn(1L);
        when(application.getUser()).thenReturn(user);
        when(application.getCompany()).thenReturn("Tech Corp");
        when(application.getPosition()).thenReturn("Software Developer");
        when(application.getLocation()).thenReturn("Remote");
        when(application.getRequirements()).thenReturn("Java, Spring Boot");
        when(application.getJobType()).thenReturn(jobType);
        when(application.getSalary()).thenReturn(80000);
        when(application.getLink()).thenReturn("https://techcorp.com/jobs/123");
        when(application.getNotes()).thenReturn("Application notes");

        // Act: Crear un ApplicationDTO usando el constructor
        ApplicationDTO applicationDTO = new ApplicationDTO(application);

        // Assert: Verificar que los campos se asignaron correctamente
        assertEquals(1L, applicationDTO.getApplicationId());
        assertEquals(1L, applicationDTO.getUserId());
        assertEquals("Tech Corp", applicationDTO.getCompany());
        assertEquals("Software Developer", applicationDTO.getPosition());
        assertEquals("Remote", applicationDTO.getLocation());
        assertEquals("Java, Spring Boot", applicationDTO.getRequirements());
        assertEquals(2L, applicationDTO.getJobTypeId()); // Comprobamos que solo el ID de JobType se asigna
        assertEquals(80000, applicationDTO.getSalary());
        assertEquals("https://techcorp.com/jobs/123", applicationDTO.getLink());
        assertEquals("Application notes", applicationDTO.getNotes());
    }

    @Test
    void constructorShouldHandleNullJobType() {
        // Arrange: Crear objetos mockeados de User y Application (sin JobType)
        User user = mock(User.class);
        Application application = mock(Application.class);
        
        // Definir comportamientos de los mocks
        when(user.getUserId()).thenReturn(1L);
        when(application.getApplicationId()).thenReturn(1L);
        when(application.getUser()).thenReturn(user);
        when(application.getCompany()).thenReturn("Tech Corp");
        when(application.getPosition()).thenReturn("Software Developer");
        when(application.getLocation()).thenReturn("Remote");
        when(application.getRequirements()).thenReturn("Java, Spring Boot");
        when(application.getJobType()).thenReturn(null); // JobType es null
        when(application.getSalary()).thenReturn(80000);
        when(application.getLink()).thenReturn("https://techcorp.com/jobs/123");
        when(application.getNotes()).thenReturn("Application notes");

        // Act: Crear un ApplicationDTO usando el constructor
        ApplicationDTO applicationDTO = new ApplicationDTO(application);

        // Assert: Verificar que el jobTypeId no se asigna cuando jobType es null
        assertEquals(1L, applicationDTO.getApplicationId());
        assertEquals(1L, applicationDTO.getUserId());
        assertEquals("Tech Corp", applicationDTO.getCompany());
        assertEquals("Software Developer", applicationDTO.getPosition());
        assertEquals("Remote", applicationDTO.getLocation());
        assertEquals("Java, Spring Boot", applicationDTO.getRequirements());
        assertEquals(null, applicationDTO.getJobTypeId()); // El valor debería ser 0 o un valor predeterminado
        assertEquals(80000, applicationDTO.getSalary());
        assertEquals("https://techcorp.com/jobs/123", applicationDTO.getLink());
        assertEquals("Application notes", applicationDTO.getNotes());
    }
}
