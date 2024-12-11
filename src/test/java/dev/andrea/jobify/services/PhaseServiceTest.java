package dev.andrea.jobify.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import dev.andrea.jobify.models.Phase;
import dev.andrea.jobify.repositories.PhaseRepository;

@SpringBootTest
public class PhaseServiceTest {

    @Mock
    private PhaseRepository phaseRepository;

    @InjectMocks
    private PhaseService phaseService;

    private Phase phase;

    @BeforeEach
    void setUp() {
        phase = new Phase();
        phase.setPhaseId(1L);
        phase.setName("Application Received");
    }

    @Test
    void getPhaseById_ShouldReturnPhaseWhenExists() {
        // Arrange: Simular que el repositorio devuelve una fase cuando se consulta por ID
        when(phaseRepository.findById(1L)).thenReturn(java.util.Optional.of(phase));

        // Act: Llamar al método que se está probando
        Phase result = phaseService.getPhaseById(1L);

        // Assert: Verificar que la fase retornada es la correcta
        assertEquals(1L, result.getPhaseId());
        assertEquals("Application Received", result.getName());
    }

    @Test
    void getPhaseById_ShouldThrowExceptionWhenPhaseNotFound() {
        // Arrange: Simular que el repositorio no devuelve nada cuando se consulta por ID
        when(phaseRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert: Verificar que se lanza la excepción correcta cuando la fase no se encuentra
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> phaseService.getPhaseById(1L));

        assertEquals("Phase not found", exception.getMessage());
    }
}
