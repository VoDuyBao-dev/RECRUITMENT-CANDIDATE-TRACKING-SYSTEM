package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.responses.DashboardResponse;
import com.example.RecruitmentCandidateTracking.entities.JobPosition;
import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.entities.Application;
import com.example.RecruitmentCandidateTracking.enums.JobStatus;
import com.example.RecruitmentCandidateTracking.repositories.ApplicationRepository;
import com.example.RecruitmentCandidateTracking.repositories.JobPositionRepository;
import com.example.RecruitmentCandidateTracking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DashboardService {

    private final UserRepository userRepository;
    private final JobPositionRepository jobPositionRepository;
    private final ApplicationRepository applicationRepository;

    public DashboardResponse getDashboard(int year) {
        // Define year range
        LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endOfYear = LocalDateTime.of(year, 12, 31, 23, 59, 59);
        LocalDateTime startOfPrevYear = startOfYear.minusYears(1);
        LocalDateTime endOfPrevYear = endOfYear.minusYears(1);

        // Total users (all time)
        long totalUsers = userRepository.count();

        // Users registered in year and previous year
        List<User> allUsers = userRepository.findAll();
        long usersThisYear = allUsers.stream()
                .filter(u -> u.getCreatedAt() != null && !u.getCreatedAt().isBefore(startOfYear) && !u.getCreatedAt().isAfter(endOfYear))
                .count();
        long usersPrevYear = allUsers.stream()
                .filter(u -> u.getCreatedAt() != null && !u.getCreatedAt().isBefore(startOfPrevYear) && !u.getCreatedAt().isAfter(endOfPrevYear))
                .count();

        double usersChangePercent = computePercent(usersThisYear, usersPrevYear);

        // Jobs
        long totalJobs = jobPositionRepository.count();
        List<JobPosition> allJobs = jobPositionRepository.findAll();
        long newJobs = allJobs.stream()
                .filter(j -> j.getCreatedAt() != null && !j.getCreatedAt().isBefore(startOfYear) && !j.getCreatedAt().isAfter(endOfYear))
                .count();

        // Applications
        List<Application> allApplications = applicationRepository.findAll();
        long applicationsThisYear = allApplications.stream()
                .filter(a -> a.getAppliedDate() != null && !a.getAppliedDate().isBefore(startOfYear) && !a.getAppliedDate().isAfter(endOfYear))
                .count();
        long applicationsPrevYear = allApplications.stream()
                .filter(a -> a.getAppliedDate() != null && !a.getAppliedDate().isBefore(startOfPrevYear) && !a.getAppliedDate().isAfter(endOfPrevYear))
                .count();
        double applicationChangePercent = computePercent(applicationsThisYear, applicationsPrevYear);

        // Staff: users that have one of staff roles and enabled = true
        Set<String> staffRoles = Set.of("ADMIN", "HR", "INTERVIEWER");
        long activeStaff = allUsers.stream()
                .filter(u -> Boolean.TRUE.equals(u.getEnabled()))
                .filter(u -> u.getRoles() != null && u.getRoles().stream().anyMatch(staffRoles::contains))
                .count();
        long newStaff = allUsers.stream()
                .filter(u -> u.getCreatedAt() != null && !u.getCreatedAt().isBefore(startOfYear) && !u.getCreatedAt().isAfter(endOfYear))
                .filter(u -> u.getRoles() != null && u.getRoles().stream().anyMatch(staffRoles::contains))
                .count();

        // Recruitment status (open / closed)
        long open = jobPositionRepository.countByStatus(JobStatus.OPEN);
        long closed = jobPositionRepository.countByStatus(JobStatus.CLOSED);

        DashboardResponse.RecruitmentStatus recruitmentStatus = DashboardResponse.RecruitmentStatus.builder()
                .open(open)
                .closed(closed)
                .build();

        // Top applied positions for the year
        Map<String, Long> applicationsByPosition = allApplications.stream()
                .filter(a -> a.getAppliedDate() != null && !a.getAppliedDate().isBefore(startOfYear) && !a.getAppliedDate().isAfter(endOfYear))
                .filter(a -> a.getJob() != null)
                .map(a -> a.getJob().getTitle())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<DashboardResponse.TopAppliedPosition> topApplied = applicationsByPosition.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(4)
                .map(e -> DashboardResponse.TopAppliedPosition.builder()
                        .position(e.getKey())
                        .applications(e.getValue())
                        .build())
                .toList();

        return DashboardResponse.builder()
                .year(year)
                .totalUsers(totalUsers)
                .usersChangePercent(round(usersChangePercent))
                .totalJobs(totalJobs)
                .newJobs(newJobs)
                .applicationRate(applicationsThisYear)
                .applicationChangePercent(round(applicationChangePercent))
                .activeStaff(activeStaff)
                .newStaff(newStaff)
                .recruitmentStatus(recruitmentStatus)
                .topAppliedPositions(topApplied)
                .build();
    }

    private double computePercent(long current, long previous) {
        if (previous == 0) {
            if (current == 0) return 0.0;
            return 100.0;
        }
        return ((double) (current - previous) / previous) * 100.0;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
