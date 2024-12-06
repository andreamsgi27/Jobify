package dev.andrea.jobify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrea.jobify.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
}