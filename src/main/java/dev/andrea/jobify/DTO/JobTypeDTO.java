package dev.andrea.jobify.DTO;

import dev.andrea.jobify.model.JobType;
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