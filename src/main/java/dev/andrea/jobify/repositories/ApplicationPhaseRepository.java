package dev.andrea.jobify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrea.jobify.models.ApplicationPhase;

public interface ApplicationPhaseRepository extends JpaRepository<ApplicationPhase, Long> {
    List<ApplicationPhase> findByApplication_applicationId(Long applicationId);
    List<ApplicationPhase> findByPhase_PhaseId(Long phaseId);
}
