package dev.andrea.jobify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrea.jobify.models.Phase;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
}
