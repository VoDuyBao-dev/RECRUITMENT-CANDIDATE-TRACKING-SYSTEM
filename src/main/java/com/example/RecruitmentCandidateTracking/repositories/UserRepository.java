package com.example.RecruitmentCandidateTracking.repositories;

import com.example.RecruitmentCandidateTracking.dto.responses.InterviewersResponse;
import com.example.RecruitmentCandidateTracking.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findByEnabledFalseAndActivationExpiryTimeBefore(LocalDateTime expiryTime);

    @Query("""
                SELECT DISTINCT u
                FROM User u
                JOIN u.roles r
                WHERE r = :role
                  AND u.enabled = true
                  AND u.status = com.example.RecruitmentCandidateTracking.enums.UserStatus.ACTIVE
            """)
    List<User> findAllInterviewers(@Param("role") String role);

}
