package dev.andrea.jobify.DTOs;

import java.time.LocalDate;

import dev.andrea.jobify.models.Application;
import dev.andrea.jobify.models.ApplicationPhase;
import dev.andrea.jobify.models.Phase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationPhaseDTO {
    private Long appPhaseId;
    private Phase phase;
    private Application application;
    private LocalDate date;

    public ApplicationPhaseDTO(ApplicationPhase applicationPhase){
        this.appPhaseId = applicationPhase.getAppPhaseId();
        this.phase = applicationPhase.getPhase();
        this.application = applicationPhase.getApplication();
        this.date = applicationPhase.getDate();
    }
}
