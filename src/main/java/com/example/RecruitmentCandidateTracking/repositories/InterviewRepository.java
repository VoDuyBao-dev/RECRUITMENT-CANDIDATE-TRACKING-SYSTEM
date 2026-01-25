package com.example.RecruitmentCandidateTracking.repositories;

import com.example.RecruitmentCandidateTracking.entities.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    
    /**
     * Find all interviews for a specific application
     */
    @Query("SELECT i FROM Interview i WHERE i.application.id = :applicationId ORDER BY i.roundNumber")
    List<Interview> findByApplicationId(@Param("applicationId") Long applicationId);
    
    /**
     * Find interviews for a specific interviewer
     */
    @Query("SELECT i FROM Interview i JOIN i.interviewers u WHERE u.id = :interviewerId ORDER BY i.scheduledTime")
    List<Interview> findByInterviewerId(@Param("interviewerId") Long interviewerId);
    
    /**
     * Check time conflict for an interviewer
     * Find interviews where the new time overlaps with existing interviews
     */
    @Query("SELECT i FROM Interview i " +
           "JOIN i.interviewers u " +
           "WHERE u.id = :interviewerId " +
           "AND ((i.scheduledTime < :endTime AND i.endTime > :startTime))")
    List<Interview> findConflictingInterviews(
        @Param("interviewerId") Long interviewerId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
    
    /**
     * Find upcoming interviews for an interviewer
     */
    @Query("SELECT i FROM Interview i " +
           "JOIN i.interviewers u " +
           "WHERE u.id = :interviewerId " +
           "AND i.scheduledTime >= :now " +
           "ORDER BY i.scheduledTime")
    List<Interview> findUpcomingInterviewsByInterviewer(
        @Param("interviewerId") Long interviewerId,
        @Param("now") LocalDateTime now
    );
    
    /**
     * Find interviews by round number for an application
     */
    @Query("SELECT i FROM Interview i " +
           "WHERE i.application.id = :applicationId " +
           "AND i.roundNumber = :roundNumber")
    List<Interview> findByApplicationIdAndRoundNumber(
        @Param("applicationId") Long applicationId,
        @Param("roundNumber") Integer roundNumber
    );
}