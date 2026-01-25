package com.example.RecruitmentCandidateTracking.dto.responses;

import com.example.RecruitmentCandidateTracking.enums.InterviewType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponse {
    
    private Long id;
    
    // Application info
    private Long applicationId;
    private String candidateName;
    private String jobTitle;
    
    // Interview details
    private Integer roundNumber;
    private String roundName;
    private LocalDateTime scheduledTime;
    private LocalDateTime endTime;
    private InterviewType interviewType;
    
    // Interviewers
    private Set<InterviewerInfo> interviewers;
    
    // Evaluation status
    private Boolean hasEvaluation;
    
    // Timestamps
    private LocalDateTime createdAt;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InterviewerInfo {
        private Long id;
        private String fullName;
        private String email;
    }
}