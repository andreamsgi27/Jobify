package dev.andrea.jobify.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PhaseDTOTest {

    @Test
    void testConstructorAndGetters() {
        Long phaseId = 1L;
        String name = "Initial Phase";
        PhaseDTO phaseDTO = new PhaseDTO(phaseId, name);

        assertEquals(phaseId, phaseDTO.getPhaseId());
        assertEquals(name, phaseDTO.getName());
    }

    @Test
    void testSetters() {
        PhaseDTO phaseDTO = new PhaseDTO();

        phaseDTO.setPhaseId(2L);
        phaseDTO.setName("Updated Phase");

        assertEquals(2L, phaseDTO.getPhaseId());
        assertEquals("Updated Phase", phaseDTO.getName());
    }
}