package com.example.RecruitmentCandidateTracking.dto.responses;

import com.example.RecruitmentCandidateTracking.enums.PipelineStage;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateAppliedJobResponse {
    // Application
    private Long applicationId;
    private PipelineStage currentStage;
    private LocalDateTime appliedDate;

    // Job
    private Long jobId;
    private String jobTitle;
    private String address;
    private String salary;

    // chỉ có khi INTERVIEWING
    private InterviewScheduleResponse interviewSchedule;
}
