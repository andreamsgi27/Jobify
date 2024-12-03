package dev.andrea.jobify.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String company;
    private String position;
    private String location;
    private String requirements;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private JobType jobType;

    @ManyToOne
    @JoinColumn(name = "phase_id", nullable = false)
    private Phase phase;

    private LocalDate phaseDate;  // Fecha de la última actualización de la fase

    private int salary;
    private String link;
    private String notes;

    // Método para cambiar la fase y actualizar la fecha
    public void changePhase(Phase newPhase) {
        this.phase = newPhase;
        this.phaseDate = LocalDate.now();  // Actualiza la fecha a la actual
    }
}
