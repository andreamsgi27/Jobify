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
@Table(name = "application_phase")
public class ApplicationPhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appPhaseId;

    @ManyToOne
    @JoinColumn(name = "phase_id", nullable = false)
    private Phase phase;
    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    private LocalDate date;
}
