package dev.andrea.jobify.DTO;

import java.time.LocalDate;

import dev.andrea.jobify.model.Application;
import dev.andrea.jobify.model.ApplicationPhase;
import dev.andrea.jobify.model.Phase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
