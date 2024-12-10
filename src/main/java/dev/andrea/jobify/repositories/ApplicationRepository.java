package dev.andrea.jobify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.andrea.jobify.models.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser_UserId(Long userId);
    
    @Query("SELECT a FROM Application a WHERE " +
        "(a.company LIKE %:keyword% OR " +
        "a.position LIKE %:keyword% OR " +
        "a.location LIKE %:keyword% OR " +
        "a.requirements LIKE %:keyword%) " +
        "AND a.user.userId = :userId")
    List<Application> findByKeywordAndUserId(@Param("keyword") String keyword, @Param("userId") Long userId);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.user.userId = :userId")
    int countApplicationsByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT a.company FROM Application a WHERE a.user.userId = :userId")
    List<String> findDistinctCompaniesByUserId(@Param("userId") Long userId);

    int countByUser_UserId(Long userId);
}
