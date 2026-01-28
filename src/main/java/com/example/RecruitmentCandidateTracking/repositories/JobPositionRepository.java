package com.example.RecruitmentCandidateTracking.repositories;

import com.example.RecruitmentCandidateTracking.entities.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
}
