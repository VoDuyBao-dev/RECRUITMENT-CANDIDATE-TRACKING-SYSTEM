package com.example.RecruitmentCandidateTracking.controller.interviewer;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.InterviewerDashboardResponse;
import com.example.RecruitmentCandidateTracking.services.InterviewerDashboardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/interviewer/dashboard")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InterviewerDashboardController {

    InterviewerDashboardService interviewerDashboardService;

    @GetMapping
    public ApiResponse<InterviewerDashboardResponse> getDashboard(@RequestParam(required = false) String month) {
        String queryMonth = (month == null || month.isBlank()) ? YearMonth.now().toString() : month;

        InterviewerDashboardResponse response = interviewerDashboardService.getInterviewerDashboard(queryMonth);

        return ApiResponse.<InterviewerDashboardResponse>builder()
                .code(HttpStatus.OK.value())
                .message("lấy các thống kê dashboard Interviewer thành công")
                .result(response)
                .build();
    }
}

