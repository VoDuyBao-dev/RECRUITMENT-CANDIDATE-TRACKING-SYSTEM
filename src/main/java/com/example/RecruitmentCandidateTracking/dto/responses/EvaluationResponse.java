package com.example.RecruitmentCandidateTracking.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationResponse {
    
    private Long id;
    
    // Interview info
    private Long interviewId;
    private Integer roundNumber;
    private String roundName;
    
    // Interviewer info
    private Long interviewerId;
    private String interviewerName;
    
    // Evaluation details
    private Integer score;
    private String comment;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDate updatedAt;
}