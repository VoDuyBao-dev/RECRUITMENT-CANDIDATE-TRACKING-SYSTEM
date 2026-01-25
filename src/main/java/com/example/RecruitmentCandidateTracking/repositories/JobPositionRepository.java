package com.example.RecruitmentCandidateTracking.repositories;

import com.example.RecruitmentCandidateTracking.entities.JobPosition;
import com.example.RecruitmentCandidateTracking.enums.JobStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {

    // Find all jobs by status
    List<JobPosition> findByStatus(JobStatus status);

    // Find all jobs for internal view (HR/Admin) - ordered by latest first
    @Query("SELECT j FROM JobPosition j ORDER BY j.createdAt DESC")
    Page<JobPosition> findAllInternal(Pageable pageable);

    // Find job by ID and status
    Optional<JobPosition> findByIdAndStatus(Long id, JobStatus status);

    // Find jobs by location (address)
    List<JobPosition> findByStatusAndAddressContainingIgnoreCase(JobStatus status, String address);

    // Check if job title exists (for duplicate validation)
    boolean existsByTitleIgnoreCase(String title);

    // Count jobs by status
    long countByStatus(JobStatus status);

    // Find jobs created by specific user
    @Query("SELECT j FROM JobPosition j WHERE j.createdBy.id = :userId ORDER BY j.createdAt DESC")
    List<JobPosition> findByCreatedByUserId(@Param("userId") Long userId);

    // Find jobs within date range
    @Query("SELECT j FROM JobPosition j " +
            "WHERE j.startDate >= :startDate " +
            "AND j.deadline <= :endDate " +
            "ORDER BY j.createdAt DESC")
    List<JobPosition> findJobsInDateRange(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}