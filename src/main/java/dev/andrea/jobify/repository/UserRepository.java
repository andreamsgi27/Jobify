package dev.andrea.jobify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /* public Optional<User> findByUsername(String username);
    public boolean existsByUsername(String username); */

}