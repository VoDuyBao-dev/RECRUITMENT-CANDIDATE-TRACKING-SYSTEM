package com.example.RecruitmentCandidateTracking.dto.responses;

import com.example.RecruitmentCandidateTracking.enums.InterviewType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewScheduleResponse {
    private Long interviewId;

    private Integer roundNumber;
    private String roundName;

    private LocalDateTime scheduledTime;
    private LocalDateTime endTime;

    private InterviewType interviewType;

    private Long applicationId;
}
