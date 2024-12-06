package dev.andrea.jobify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrea.jobify.model.JobType;


public interface JobTypeRepository extends JpaRepository<JobType, Long> {

}