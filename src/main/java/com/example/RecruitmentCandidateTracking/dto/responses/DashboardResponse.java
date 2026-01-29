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
public class DashboardResponse {
    private int year;
    private long totalUsers;
    private double usersChangePercent;
    private long totalJobs;
    private long newJobs;
    private long applicationRate; // total applications
    private double applicationChangePercent;
    private long activeStaff;
    private long newStaff;
    private RecruitmentStatus recruitmentStatus;
    private List<TopAppliedPosition> topAppliedPositions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecruitmentStatus {
        private long open;
        private long closed;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TopAppliedPosition {
        private String position;
        private long applications;
    }
}

