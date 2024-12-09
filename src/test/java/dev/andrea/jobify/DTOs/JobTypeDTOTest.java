package dev.andrea.jobify.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class JobTypeDTOTest {
    
    @Test
    public void testJobTypeDTO() {
        JobTypeDTO jobTypeDTO = new JobTypeDTO();
        jobTypeDTO.setJobTypeId(1L);
        jobTypeDTO.setName("Remoto");

        assertEquals(1L, jobTypeDTO.getJobTypeId());
        assertEquals("Remoto", jobTypeDTO.getName());
    }
}
