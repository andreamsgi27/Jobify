package dev.andrea.jobify.DTO;

import java.time.LocalDate;
import dev.andrea.jobify.model.Application;
import dev.andrea.jobify.model.JobType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationDTO {
    private Long appId;
    private Long userId;
    private String company;
    private String position;
    private String location;
    private String requirements;
    private JobType jobType;
    private LocalDate phaseDate;
    private int salary;
    private String link;
    private String notes;
    
    public ApplicationDTO(Application application) {
        this.appId = application.getApplicationId();
        this.userId = application.getUser().getUserId();
        this.company = application.getCompany();
        this.position = application.getPosition();
        this.location = application.getLocation();
        this.requirements = application.getRequirements();
        this.jobType = application.getJobType();
        this.phaseDate = application.getPhaseDate();
        this.salary = application.getSalary();
        this.link = application.getLink();
        this.notes = application.getNotes();
    }
    
}
