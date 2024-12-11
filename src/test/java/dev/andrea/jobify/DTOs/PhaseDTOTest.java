package dev.andrea.jobify.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PhaseDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Crear un objeto PhaseDTO usando el constructor
        Long phaseId = 1L;
        String name = "Initial Phase";
        PhaseDTO phaseDTO = new PhaseDTO(phaseId, name);

        // Verificar que los valores están correctamente asignados
        assertEquals(phaseId, phaseDTO.getPhaseId());
        assertEquals(name, phaseDTO.getName());
    }

    @Test
    void testSetters() {
        // Crear un objeto PhaseDTO vacío
        PhaseDTO phaseDTO = new PhaseDTO();

        // Usar los setters para asignar valores
        phaseDTO.setPhaseId(2L);
        phaseDTO.setName("Updated Phase");

        // Verificar que los valores están correctamente asignados
        assertEquals(2L, phaseDTO.getPhaseId());
        assertEquals("Updated Phase", phaseDTO.getName());
    }
}