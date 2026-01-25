package com.example.RecruitmentCandidateTracking.repositories;

import com.example.RecruitmentCandidateTracking.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    
    /**
     * Find evaluation by interview and interviewer
     */
    @Query("SELECT e FROM Evaluation e " +
           "WHERE e.interview.id = :interviewId " +
           "AND e.interviewer.id = :interviewerId")
    Optional<Evaluation> findByInterviewIdAndInterviewerId(
        @Param("interviewId") Long interviewId,
        @Param("interviewerId") Long interviewerId
    );
    
    /**
     * Find all evaluations for an interview
     */
    @Query("SELECT e FROM Evaluation e WHERE e.interview.id = :interviewId")
    List<Evaluation> findByInterviewId(@Param("interviewId") Long interviewId);
    
    /**
     * Check if evaluation exists for interview and interviewer
     */
    boolean existsByInterviewIdAndInterviewerId(Long interviewId, Long interviewerId);
    
    /**
     * Find all evaluations by an interviewer
     */
    @Query("SELECT e FROM Evaluation e WHERE e.interviewer.id = :interviewerId ORDER BY e.createdAt DESC")
    List<Evaluation> findByInterviewerId(@Param("interviewerId") Long interviewerId);
    
    /**
     * Get average score for an interview
     */
    @Query("SELECT AVG(e.score) FROM Evaluation e WHERE e.interview.id = :interviewId")
    Double getAverageScoreByInterviewId(@Param("interviewId") Long interviewId);
}