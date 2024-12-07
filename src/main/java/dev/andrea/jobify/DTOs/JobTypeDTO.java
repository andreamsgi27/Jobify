package dev.andrea.jobify.DTOs;

import dev.andrea.jobify.models.JobType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobTypeDTO {
    private Long jobTypeId;
    private String name;

    public JobTypeDTO(JobType jobType) {
        this.jobTypeId = jobType.getJobTypeId();
        this.name = jobType.getName();
    }
}