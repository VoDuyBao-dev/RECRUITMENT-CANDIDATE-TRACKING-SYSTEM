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
public class InterviewerDashboardResponse {
    private String month; // YYYY-MM
    private InterviewerInfo interviewerInfo;
    private TodayOverview todayOverview;
    private MonthlyCalendar monthlyCalendar;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InterviewerInfo {
        private String fullName;
        private String role;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TodayOverview {
        private long totalInterviews;
        private long pending;
        private long completed;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MonthlyCalendar {
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

