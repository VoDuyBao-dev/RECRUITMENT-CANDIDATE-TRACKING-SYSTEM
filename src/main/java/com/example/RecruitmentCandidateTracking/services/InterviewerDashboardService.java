package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.responses.InterviewerDashboardResponse;
import com.example.RecruitmentCandidateTracking.entities.Interview;
import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.repositories.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewerDashboardService {

    private final InterviewRepository interviewRepository;
    private final AuthenticationService authenticationService;

    public InterviewerDashboardResponse getInterviewerDashboard(String month) {
        // Get current interviewer
        User current = authenticationService.getCurrentUser();
        Long interviewerId = current.getId();

        // Parse month window
        YearMonth ym = YearMonth.parse(month);
        LocalDateTime startOfMonth = ym.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = ym.atEndOfMonth().atTime(23, 59, 59);

        // Interviewer info
        String role = current.getRoles() != null && !current.getRoles().isEmpty()
                ? current.getRoles().iterator().next()
                : "INTERVIEWER";
        String roleLabel = mapRoleToLabel(role);

        InterviewerDashboardResponse.InterviewerInfo interviewerInfo = InterviewerDashboardResponse.InterviewerInfo.builder()
                .fullName(current.getFullName())
                .role(roleLabel)
                .build();

        // Fetch all interviews for this interviewer once
        List<Interview> allForInterviewer = interviewRepository.findAllByInterviewerId(interviewerId);

        // Today's overview
        LocalDate today = LocalDate.now();
        LocalDateTime startToday = today.atStartOfDay();
        LocalDateTime endToday = today.atTime(23, 59, 59);

        List<Interview> interviewsTodayList = allForInterviewer.stream()
                .filter(i -> Objects.nonNull(i.getScheduledTime()))
                .filter(i -> !i.getScheduledTime().isBefore(startToday) && !i.getScheduledTime().isAfter(endToday))
                .collect(Collectors.toList());

        long totalInterviews = interviewsTodayList.size();
        LocalDateTime now = LocalDateTime.now();
        long completed = interviewsTodayList.stream()
                .filter(i -> Objects.nonNull(i.getEndTime()))
                .filter(i -> !i.getEndTime().isAfter(now)) // endTime <= now
                .count();
        long pending = totalInterviews - completed;

        InterviewerDashboardResponse.TodayOverview todayOverview = InterviewerDashboardResponse.TodayOverview.builder()
                .totalInterviews(totalInterviews)
                .pending(pending)
                .completed(completed)
                .build();

        // Monthly calendar: interviews in the month grouped by day-of-month
        List<Interview> interviewsInMonth = allForInterviewer.stream()
                .filter(i -> Objects.nonNull(i.getScheduledTime()))
                .filter(i -> !i.getScheduledTime().isBefore(startOfMonth) && !i.getScheduledTime().isAfter(endOfMonth))
                .collect(Collectors.toList());

        Map<Integer, Long> countsByDay = interviewsInMonth.stream()
                .collect(Collectors.groupingBy(i -> i.getScheduledTime().getDayOfMonth(), Collectors.counting()));

        List<InterviewerDashboardResponse.DayCount> daysWithInterviews = countsByDay.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> InterviewerDashboardResponse.DayCount.builder()
                        .day(e.getKey())
                        .count(e.getValue())
                        .build())
                .collect(Collectors.toList());

        InterviewerDashboardResponse.MonthlyCalendar monthlyCalendar = InterviewerDashboardResponse.MonthlyCalendar.builder()
                .daysWithInterviews(daysWithInterviews)
                .build();

        return InterviewerDashboardResponse.builder()
                .month(month)
                .interviewerInfo(interviewerInfo)
                .todayOverview(todayOverview)
                .monthlyCalendar(monthlyCalendar)
                .build();
    }

    private String mapRoleToLabel(String role) {
        if (role == null) return "Interviewer";
        switch (role) {
            case "ADMIN": return "Admin";
            case "HR": return "HR";
            case "INTERVIEWER": return "Interviewer";
            case "CANDIDATE": return "Candidate";
            default: return role;
        }
    }
}

