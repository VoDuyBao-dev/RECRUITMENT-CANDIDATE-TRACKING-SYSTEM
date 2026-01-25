package com.example.RecruitmentCandidateTracking.repositories;

import com.example.RecruitmentCandidateTracking.entities.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
