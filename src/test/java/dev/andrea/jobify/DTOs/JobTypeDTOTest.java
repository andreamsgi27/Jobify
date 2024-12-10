package dev.andrea.jobify.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dev.andrea.jobify.models.JobType;

public class JobTypeDTOTest {
    
    @Test
    public void testJobTypeDTO() {
        JobTypeDTO jobTypeDTO = new JobTypeDTO();
        jobTypeDTO.setJobTypeId(1L);
        jobTypeDTO.setName("Remoto");

        assertEquals(1L, jobTypeDTO.getJobTypeId());
        assertEquals("Remoto", jobTypeDTO.getName());
    }

    @Test
    void constructorShouldMapFieldsCorrectly() {
        // Arrange: Crear un objeto JobType con datos simulados
        JobType jobType = new JobType();
        jobType.setJobTypeId(1L);
        jobType.setName("Full-Time");

        // Act: Usar el constructor para crear un JobTypeDTO
        JobTypeDTO jobTypeDTO = new JobTypeDTO(jobType);

        // Assert: Verificar que los campos fueron mapeados correctamente
        assertEquals(1L, jobTypeDTO.getJobTypeId());
        assertEquals("Full-Time", jobTypeDTO.getName());
    }
}
