package dev.andrea.jobify.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrea.jobify.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}