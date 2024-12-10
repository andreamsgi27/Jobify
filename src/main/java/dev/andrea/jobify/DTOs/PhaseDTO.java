package dev.andrea.jobify.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class PhaseDTO {
    private Long phaseId;
    private String name;

    public PhaseDTO(Long phaseId, String name) {
        this.phaseId = phaseId;
        this.name = name;
    }
}
