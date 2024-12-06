package dev.andrea.jobify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
/* import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; */

import dev.andrea.jobify.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser_UserId(Long userId);
    
   /*  @Query("SELECT a FROM Application a WHERE (a.companyName LIKE %:keyword% OR a.position LIKE %:keyword%) AND a.user.userId = :userId")
    List<Application> findByKeywordAndUserId(@Param("keyword") String keyword, @Param("userId") Long userId); */
}
