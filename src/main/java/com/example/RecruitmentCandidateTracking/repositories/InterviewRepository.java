package com.example.RecruitmentCandidateTracking.repositories;

import com.example.RecruitmentCandidateTracking.entities.Interview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    
// tìm interviews theo applicationId với phân trang
    @Query("SELECT i FROM Interview i WHERE i.application.id = :applicationId ORDER BY i.roundNumber")
    Page<Interview> findByApplicationId(@Param("applicationId") Long applicationId, Pageable pageable);
    
// tìm interviews theo interviewerId với phân trang
    @Query("SELECT i FROM Interview i JOIN i.interviewers u WHERE u.id = :interviewerId ORDER BY i.scheduledTime")
    Page<Interview> findByInterviewerId(@Param("interviewerId") Long interviewerId, Pageable pageable);
    
// tìm interviews có xung đột lịch theo interviewerId và khoảng thời gian
    @Query("SELECT i FROM Interview i " +
           "JOIN i.interviewers u " +
           "WHERE u.id = :interviewerId " +
           "AND ((i.scheduledTime < :endTime AND i.endTime > :startTime))")
    List<Interview> findConflictingInterviews(
        @Param("interviewerId") Long interviewerId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
    
// tìm các interviews sắp tới của một interviewer
    @Query("SELECT i FROM Interview i " +
           "JOIN i.interviewers u " +
           "WHERE u.id = :interviewerId " +
           "AND i.scheduledTime >= :now " +
           "ORDER BY i.scheduledTime")
    List<Interview> findUpcomingInterviewsByInterviewer(
        @Param("interviewerId") Long interviewerId,
        @Param("now") LocalDateTime now
    );
    
// tìm interviews theo applicationId và roundNumber
    @Query("SELECT i FROM Interview i " +
           "WHERE i.application.id = :applicationId " +
           "AND i.roundNumber = :roundNumber")
    List<Interview> findByApplicationIdAndRoundNumber(
        @Param("applicationId") Long applicationId,
        @Param("roundNumber") Integer roundNumber
    );

    @EntityGraph(attributePaths = {
            "application",
            "application.candidate",
            "application.job"
    })
    Optional<Interview> findDetailById(Long id);


    @Query("SELECT i FROM Interview i JOIN i.interviewers u WHERE u.id = :interviewerId")
    List<Interview> findAllByInterviewerId(@Param("interviewerId") Long interviewerId);
}