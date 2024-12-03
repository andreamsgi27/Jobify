package dev.andrea.jobify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrea.jobify.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
