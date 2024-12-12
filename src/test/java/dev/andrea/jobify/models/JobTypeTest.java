package dev.andrea.jobify.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JobTypeTest {

    private JobType jobType;

    @BeforeEach
    void setUp() {
        jobType = new JobType("Full-Time");
    }

    @Test
    void testConstructorWithName() {
        assertNotNull(jobType);
        assertEquals("Full-Time", jobType.getName(), "El nombre del trabajo debería ser 'Full-Time'");
    }

    @Test
    void testSetName() {
        jobType.setName("Part-Time");
        assertEquals("Part-Time", jobType.getName(), "El nombre del trabajo debería ser 'Part-Time' después de usar el setter");
    }

    @Test
    void testConstructorCopy() {
        JobType copiedJobType = new JobType(jobType);

        assertNotNull(copiedJobType);
        assertEquals(jobType.getJobTypeId(), copiedJobType.getJobTypeId(), "El ID de trabajo debería ser el mismo");
        assertEquals(jobType.getName(), copiedJobType.getName(), "El nombre del trabajo debería ser el mismo");
    }

    @Test
    void testBuilder() {
        JobType jobTypeUsingBuilder = JobType.builder()
                                            .name("Contract")
                                            .build();

        assertNotNull(jobTypeUsingBuilder);
        assertEquals("Contract", jobTypeUsingBuilder.getName(), "El nombre del trabajo debería ser 'Contract' utilizando el builder");
    }

    @Test
    void testEqualsAndHashCode() {
        JobType anotherJobType = new JobType("Full-Time");

        assertEquals(jobType, anotherJobType, "Los objetos deberían ser iguales ya que tienen el mismo nombre");
        assertEquals(jobType.hashCode(), anotherJobType.hashCode(), "Los hashCodes deberían ser iguales para objetos con el mismo nombre");
    }

    @Test
    void testToString() {
        String expectedToString = "JobType(jobTypeId=null, name=Full-Time)";
        assertEquals(expectedToString, jobType.toString(), "El método toString debería generar una cadena de texto correctamente");
    }
}