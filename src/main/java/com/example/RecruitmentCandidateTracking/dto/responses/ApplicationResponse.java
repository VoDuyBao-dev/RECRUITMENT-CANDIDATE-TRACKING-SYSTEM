package com.example.RecruitmentCandidateTracking.dto.responses;

import com.example.RecruitmentCandidateTracking.enums.PipelineStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {

    private Long id;

    // Candidate info
    private Long candidateId;
    private String candidateName;
    private String candidateEmail;

    // Job info
    private Long jobId;
    private String jobTitle;

    // Application details
    private String resumePath;
    private PipelineStage currentStage;
    private LocalDateTime appliedDate;

    // Interview count
    private Integer totalInterviews;
}