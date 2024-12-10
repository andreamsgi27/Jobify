package dev.andrea.jobify.DTOs;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dev.andrea.jobify.models.Application;
import dev.andrea.jobify.models.ApplicationPhase;
import dev.andrea.jobify.models.Phase;

class ApplicationPhaseDTOTest {

    @Test
    void constructorShouldMapFieldsCorrectly() {
        // Arrange: Crear objetos Phase y Application
        Phase phase = new Phase();
        phase.setPhaseId(1L);
        phase.setName("Interview");

        Application application = new Application();
        application.setApplicationId(1L);
        application.setPosition("Software Developer");

        // Crear un objeto ApplicationPhase con datos simulados
        ApplicationPhase applicationPhase = new ApplicationPhase();
        applicationPhase.setAppPhaseId(1L);
        applicationPhase.setPhase(phase);
        applicationPhase.setApplication(application);
        applicationPhase.setDate(LocalDate.of(2023, 12, 10));

        ApplicationPhaseDTO applicationPhaseDTO = new ApplicationPhaseDTO(applicationPhase);

        assertEquals(1L, applicationPhaseDTO.getAppPhaseId());
        assertEquals(phase, applicationPhaseDTO.getPhase());
        assertEquals(application, applicationPhaseDTO.getApplication());
        assertEquals(LocalDate.of(2023, 12, 10), applicationPhaseDTO.getDate());
    }
}
