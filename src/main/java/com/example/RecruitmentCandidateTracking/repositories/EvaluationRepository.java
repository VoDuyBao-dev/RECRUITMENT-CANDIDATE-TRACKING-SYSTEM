package com.example.RecruitmentCandidateTracking.repositories;

import com.example.RecruitmentCandidateTracking.entities.Evaluation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    // tìm đánh giá theo interviewId và interviewerId
    @Query("SELECT e FROM Evaluation e " +
            "WHERE e.interview.id = :interviewId " +
            "AND e.interviewer.id = :interviewerId")
    Optional<Evaluation> findByInterviewIdAndInterviewerId(
            @Param("interviewId") Long interviewId,
            @Param("interviewerId") Long interviewerId);

    // tìm đánh giá theo interviewId với phân trang
    @Query("SELECT e FROM Evaluation e WHERE e.interview.id = :interviewId")
    Page<Evaluation> findByInterviewId(@Param("interviewId") Long interviewId, Pageable pageable);

    @Query("SELECT e FROM Evaluation e WHERE e.interview.id = :interviewId")
    List<Evaluation> findListByInterviewId(@Param("interviewId") Long interviewId);

    // kiểm tra tồn tại đánh giá theo interviewId và interviewerId
    boolean existsByInterviewIdAndInterviewerId(Long interviewId, Long interviewerId);

    // tìm đánh giá theo interviewerId
    @Query("SELECT e FROM Evaluation e WHERE e.interviewer.id = :interviewerId ORDER BY e.createdAt DESC")
    List<Evaluation> findByInterviewerId(@Param("interviewerId") Long interviewerId);

    // tính điểm trung bình theo interviewId
    @Query("SELECT AVG(e.score) FROM Evaluation e WHERE e.interview.id = :interviewId")
    Double getAverageScoreByInterviewId(@Param("interviewId") Long interviewId);

    List<Evaluation> findByInterviewId(Long interviewId);

    @Query("""
                SELECT COUNT(e)
                FROM Evaluation e
                WHERE e.interview.id = :interviewId
            """)
    long countEvaluationsByInterviewId(@Param("interviewId") Long interviewId);

}