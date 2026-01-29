package com.example.RecruitmentCandidateTracking.controller.admin;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.DashboardResponse;
import com.example.RecruitmentCandidateTracking.services.DashboardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardController {

    DashboardService dashboardService;

    @GetMapping
    public ApiResponse<DashboardResponse> getDashboard(@RequestParam(required = false) Integer year) {
        int queryYear = (year == null) ? Year.now().getValue() : year;

        DashboardResponse response = dashboardService.getDashboard(queryYear);

        return ApiResponse.<DashboardResponse>builder()
                .code(HttpStatus.OK.value())
                .message("lấy các thống kê dashboard admin thành công")
                .result(response)
                .build();
    }

}
