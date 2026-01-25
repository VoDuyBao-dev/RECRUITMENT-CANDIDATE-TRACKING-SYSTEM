package com.example.RecruitmentCandidateTracking.repositories;

import com.example.RecruitmentCandidateTracking.entities.Application;
import com.example.RecruitmentCandidateTracking.enums.PipelineStage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a WHERE a.job.id = :jobId ORDER BY a.appliedDate DESC")
    Page<Application> findByJobId(@Param("jobId") Long jobId, Pageable pageable);

    Page<Application> findByCurrentStage(PipelineStage stage, Pageable pageable);
    
    @Query("SELECT a FROM Application a WHERE a.job.id = :jobId AND a.currentStage = :stage ORDER BY a.appliedDate DESC")
    List<Application> findByJobIdAndStage(@Param("jobId") Long jobId, @Param("stage") PipelineStage stage);
    
    long countByJobId(Long jobId);
    
    long countByCurrentStage(PipelineStage stage);
}