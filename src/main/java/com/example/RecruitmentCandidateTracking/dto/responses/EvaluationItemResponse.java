package com.example.RecruitmentCandidateTracking.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationItemResponse {

    private Long evaluationId;

    private Long interviewerId;
    private String interviewerName;

    private int score;
    private String comment;

    private LocalDate updatedAt;
}
