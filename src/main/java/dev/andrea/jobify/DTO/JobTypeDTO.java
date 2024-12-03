package dev.andrea.jobify.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobTypeDTO {
    private Long jobTypeId;
    private String name;

    public JobTypeDTO(Long jobTypeId, String name) {
        this.jobTypeId = jobTypeId;
        this.name = name;
    }
}