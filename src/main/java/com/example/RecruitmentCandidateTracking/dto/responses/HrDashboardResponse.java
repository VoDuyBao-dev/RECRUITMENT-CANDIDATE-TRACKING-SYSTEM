package com.example.RecruitmentCandidateTracking.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HrDashboardResponse {
    private String month; // format YYYY-MM
    private long pendingCVs;
    private long openJobs;
    private long interviewsToday;
    private InterviewCalendar interviewCalendar;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InterviewCalendar {
        private List<DayCount> daysWithInterviews;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DayCount {
        private int day;
        private long count;
    }
}

