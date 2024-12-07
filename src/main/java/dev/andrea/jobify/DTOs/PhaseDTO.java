package dev.andrea.jobify.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhaseDTO {
    private Long phaseId;
    private String name;

    public PhaseDTO(Long phaseId, String name) {
        this.phaseId = phaseId;
        this.name = name;
    }
}
