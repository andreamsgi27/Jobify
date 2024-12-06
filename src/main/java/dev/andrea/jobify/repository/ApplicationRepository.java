package dev.andrea.jobify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrea.jobify.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUserId(Long userId);
    List<Application> findByKeywordAndUserId(String keyword, Long userId);
}
