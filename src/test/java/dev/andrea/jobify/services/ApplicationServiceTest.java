package dev.andrea.jobify.services;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import dev.andrea.jobify.DTOs.ApplicationDTO;
import dev.andrea.jobify.models.Application;
import dev.andrea.jobify.models.JobType;
import dev.andrea.jobify.models.User;
import dev.andrea.jobify.repositories.ApplicationRepository;
import dev.andrea.jobify.repositories.JobTypeRepository;
import dev.andrea.jobify.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private JobTypeRepository jobTypeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationService applicationService;

    private User user;
    private JobType jobType;
    private ApplicationDTO applicationDTO;

    @BeforeEach
    void setUp() {
        // Crear objetos de prueba
        user = new User(1L, "testuser", "password", "testuser@example.com");
        jobType = new JobType(1L, "Remote");

        // Crear ApplicationDTO de ejemplo con el userId y jobTypeId correctos
        applicationDTO = new ApplicationDTO();
        applicationDTO.setUserId(user.getUserId());
        applicationDTO.setCompany("Company");
        applicationDTO.setPosition("Developer");
        applicationDTO.setLocation("Location");
        applicationDTO.setRequirements("Requirements");
        applicationDTO.setJobTypeId(jobType.getJobTypeId());
        applicationDTO.setSalary(50000);
        applicationDTO.setLink("Link");
        applicationDTO.setNotes("Notes");
    }

    @Test
    void testCreateApp() {
        Application savedApplication = new Application(1L, user, "Mock Company", "Developer", "Location", "Requirements", jobType, 50000, "mockLink", "mockNotes");
        when(applicationRepository.save(any(Application.class))).thenReturn(savedApplication);

        // Mockear el SecurityContextHolder
        try (MockedStatic<SecurityContextHolder> securityContextMock = Mockito.mockStatic(SecurityContextHolder.class)) {
            // Mockear el SecurityContext y la autenticación
            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);
            
            // Configurar el comportamiento del mock para que devuelva el nombre del usuario
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("mockedUser");
            
            // Establecer el SecurityContext con el mock
            securityContextMock.when(() -> SecurityContextHolder.getContext()).thenReturn(securityContext);

            // Simulamos la existencia del usuario en el repositorio
            when(userRepository.existsByUsername("mockedUser")).thenReturn(true); // Usuario existe
            when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user)); // Obtener usuario mockeado
            when(jobTypeRepository.findById(anyLong())).thenReturn(Optional.of(jobType)); // Simulamos un JobType

            // Llamamos al método que queremos probar
            ApplicationDTO result = applicationService.createApp(applicationDTO);

            // Verificar si el resultado es el esperado
            assertNotNull(result);
            verify(userRepository).existsByUsername("mockedUser");
            verify(userRepository).findByUsername("mockedUser");
        }
    }
}