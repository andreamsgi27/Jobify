package dev.andrea.jobify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrea.jobify.model.ApplicationPhase;

public interface ApplicationPhaseRepository extends JpaRepository<ApplicationPhase, Long> {
    List<ApplicationPhase> findByApplicationId(Long applicationId);
    List<ApplicationPhase> findByPhaseId(Long phaseId);
}
