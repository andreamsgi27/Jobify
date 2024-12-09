package dev.andrea.jobify.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PhaseDTOTest {
    
    @Test
    void testPhaseDTO() {
        PhaseDTO phasedto = new PhaseDTO();
        phasedto.setPhaseId(1L);
        phasedto.setName("Exploring");

        assertEquals(1L, phasedto.getPhaseId());
        assertEquals("Exploring", phasedto.getName());
    }
}
