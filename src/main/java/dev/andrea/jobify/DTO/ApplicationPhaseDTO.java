package dev.andrea.jobify.DTO;

import java.time.LocalDate;

import dev.andrea.jobify.model.Application;
import dev.andrea.jobify.model.Phase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationPhaseDTO {
    private Long appPhaseId;
    private Phase phase;
    private Application application;
    private LocalDate date;
    
}
