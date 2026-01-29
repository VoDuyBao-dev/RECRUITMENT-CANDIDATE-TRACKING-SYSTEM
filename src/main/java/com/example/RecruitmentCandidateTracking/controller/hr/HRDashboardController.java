package com.example.RecruitmentCandidateTracking.controller.hr;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.HrDashboardResponse;
import com.example.RecruitmentCandidateTracking.services.HrDashboardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/hr/dashboard")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HRDashboardController {

    HrDashboardService hrDashboardService;

    @GetMapping
    public ApiResponse<HrDashboardResponse> getHrDashboard(@RequestParam(required = false) String month) {
        String queryMonth = (month == null || month.isBlank()) ? YearMonth.now().toString() : month;

        HrDashboardResponse response = hrDashboardService.getHrDashboard(queryMonth);

        return ApiResponse.<HrDashboardResponse>builder()
                .code(HttpStatus.OK.value())
                .message("lấy các thống kê dashboard HR thành công")
                .result(response)
                .build();
    }
}

