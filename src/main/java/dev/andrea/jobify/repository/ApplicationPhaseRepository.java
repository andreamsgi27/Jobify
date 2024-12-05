package dev.andrea.jobify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrea.jobify.model.ApplicationPhase;

public interface ApplicationPhaseRepository extends JpaRepository<ApplicationPhase, Long> {
    
}
