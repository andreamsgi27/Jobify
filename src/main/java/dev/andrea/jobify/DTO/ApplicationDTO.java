package dev.andrea.jobify.DTO;

import dev.andrea.jobify.model.Application;
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
    private Long jobTypeId; // Solo el ID del JobType
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
        if (application.getJobType() != null) {
            this.jobTypeId = application.getJobType().getJobTypeId(); // Solo el ID de JobType
        }
        this.salary = application.getSalary();
        this.link = application.getLink();
        this.notes = application.getNotes();
    }
}
