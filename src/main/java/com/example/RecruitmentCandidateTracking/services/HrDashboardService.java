package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.responses.HrDashboardResponse;
import com.example.RecruitmentCandidateTracking.entities.Interview;
import com.example.RecruitmentCandidateTracking.enums.JobStatus;
import com.example.RecruitmentCandidateTracking.enums.PipelineStage;
import com.example.RecruitmentCandidateTracking.repositories.ApplicationRepository;
import com.example.RecruitmentCandidateTracking.repositories.InterviewRepository;
import com.example.RecruitmentCandidateTracking.repositories.JobPositionRepository;
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
public class HrDashboardService {

    private final ApplicationRepository applicationRepository;
    private final JobPositionRepository jobPositionRepository;
    private final InterviewRepository interviewRepository;

    public HrDashboardResponse getHrDashboard(String month) {
        // Parse month YYYY-MM
        YearMonth ym = YearMonth.parse(month);
        LocalDateTime startOfMonth = ym.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = ym.atEndOfMonth().atTime(23, 59, 59);

        // Pending CVs: applications currently in SCREENING
        long pendingCVs = applicationRepository.countByCurrentStage(PipelineStage.SCREENING);

        // Open jobs
        long openJobs = jobPositionRepository.countByStatus(JobStatus.OPEN);

        // Interviews today
        LocalDate today = LocalDate.now();
        LocalDateTime startToday = today.atStartOfDay();
        LocalDateTime endToday = today.atTime(23, 59, 59);

        long interviewsToday = interviewRepository.findAll().stream()
                .filter(i -> Objects.nonNull(i.getScheduledTime()))
                .filter(i -> !i.getScheduledTime().isBefore(startToday) && !i.getScheduledTime().isAfter(endToday))
                .count();

        // Interviews in the month -> group by day of month
        List<Interview> interviewsInMonth = interviewRepository.findAll().stream()
                .filter(i -> Objects.nonNull(i.getScheduledTime()))
                .filter(i -> !i.getScheduledTime().isBefore(startOfMonth) && !i.getScheduledTime().isAfter(endOfMonth))
                .toList();

        Map<Integer, Long> countsByDay = interviewsInMonth.stream()
                .collect(Collectors.groupingBy(i -> i.getScheduledTime().getDayOfMonth(), Collectors.counting()));

        List<HrDashboardResponse.DayCount> daysWithInterviews = countsByDay.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> HrDashboardResponse.DayCount.builder()
                        .day(e.getKey())
                        .count(e.getValue())
                        .build())
                .toList();

        HrDashboardResponse.InterviewCalendar calendar = HrDashboardResponse.InterviewCalendar.builder()
                .daysWithInterviews(daysWithInterviews)
                .build();

        return HrDashboardResponse.builder()
                .month(month)
                .pendingCVs(pendingCVs)
                .openJobs(openJobs)
                .interviewsToday(interviewsToday)
                .interviewCalendar(calendar)
                .build();
    }
}

