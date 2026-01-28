package com.example.RecruitmentCandidateTracking.repositories;

import com.example.RecruitmentCandidateTracking.entities.Application;
import com.example.RecruitmentCandidateTracking.entities.JobPosition;
import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.enums.PipelineStage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByCandidateAndJob(User candidate, JobPosition job);

    List<Application> findByCandidate(User candidate);

    List<Application> findByJob(JobPosition job);

    Optional<Application> findByCandidateIdAndJobId(Long candidateId, Long jobId);

    @Query("SELECT a FROM Application a WHERE a.candidate.id = :candidateId AND a.currentStage = :stage")
    List<Application> findByCandidateAndStage(@Param("candidateId") Long candidateId);

  @Query("SELECT a FROM Application a WHERE a.job.id = :jobId ORDER BY a.appliedDate DESC")
  Page<Application> findByJobId(@Param("jobId") Long jobId, Pageable pageable);

    Page<Application> findByCurrentStage(PipelineStage stage, Pageable pageable);

    @Query("SELECT a FROM Application a WHERE a.job.id = :jobId AND a.currentStage = :stage ORDER BY a.appliedDate DESC")
    List<Application> findByJobIdAndStage(@Param("jobId") Long jobId, @Param("stage") PipelineStage stage);

    long countByJobId(Long jobId);

    long countByCurrentStage(PipelineStage stage);
}