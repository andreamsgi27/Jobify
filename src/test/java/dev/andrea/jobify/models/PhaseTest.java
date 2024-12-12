package dev.andrea.jobify.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhaseTest {

    private Phase phase;

    @BeforeEach
    void setUp() {
        phase = new Phase("Applied");
    }

    @Test
    void testConstructorWithName() {
        assertNotNull(phase);
        assertEquals("Applied", phase.getName(), "El nombre de la fase debería ser 'Applied'");
    }

    @Test
    void testSetName() {
        phase.setName("Interview");
        assertEquals("Interview", phase.getName(), "El nombre de la fase debería ser 'Interview' después de usar el setter");
    }

    @Test
    void testConstructorCopy() {
        Phase copiedPhase = new Phase(phase.getName());

        assertNotNull(copiedPhase);
        assertEquals(phase.getPhaseId(), copiedPhase.getPhaseId(), "El ID de la fase debería ser el mismo");
        assertEquals(phase.getName(), copiedPhase.getName(), "El nombre de la fase debería ser el mismo");
    }

    @Test
    void testBuilder() {
        Phase phaseUsingBuilder = Phase.builder()
                                    .name("Offer")
                                    .build();

        assertNotNull(phaseUsingBuilder);
        assertEquals("Offer", phaseUsingBuilder.getName(), "El nombre de la fase debería ser 'Offer' utilizando el builder");
    }

    @Test
    void testEqualsAndHashCode() {
        Phase anotherPhase = new Phase("Applied");

        assertEquals(phase, anotherPhase, "Los objetos deberían ser iguales ya que tienen el mismo nombre");
        assertEquals(phase.hashCode(), anotherPhase.hashCode(), "Los hashCodes deberían ser iguales para objetos con el mismo nombre");
    }

    @Test
    void testToString() {
        String expectedToString = "Phase(phaseId=null, name=Applied)";
        assertEquals(expectedToString, phase.toString(), "El método toString debería generar una cadena de texto correctamente");
    }
}