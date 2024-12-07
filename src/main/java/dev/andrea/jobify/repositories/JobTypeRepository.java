package dev.andrea.jobify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrea.jobify.models.JobType;


public interface JobTypeRepository extends JpaRepository<JobType, Long> {

}