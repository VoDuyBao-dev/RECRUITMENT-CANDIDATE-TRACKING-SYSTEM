package com.example.RecruitmentCandidateTracking.dto.repsonses;

import com.example.RecruitmentCandidateTracking.enums.PipelineStage;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationResponse {
    private Long id;
    private Long candidateId;
    private Long jobId;
    private String jobTitle;
    private PipelineStage currentStage;
    private LocalDateTime appliedDate;
}