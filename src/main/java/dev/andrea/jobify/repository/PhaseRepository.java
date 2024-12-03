package dev.andrea.jobify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrea.jobify.model.Phase;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
}
