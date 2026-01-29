package com.example.RecruitmentCandidateTracking.dto.responses;

import com.example.RecruitmentCandidateTracking.enums.InterviewType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewEvaluationDetailResponse {
    // Interview info
    private Long interviewId;
    private Integer roundNumber;
    private String roundName;
    private InterviewType interviewType;
    private LocalDateTime scheduledTime;
    private LocalDateTime endTime;

    // Application & Candidate info
    private Long applicationId;
    private Long candidateId;
    private String candidateName;
    private String jobTitle;

    // Evaluations
    private List<EvaluationItemResponse> evaluations;
}
