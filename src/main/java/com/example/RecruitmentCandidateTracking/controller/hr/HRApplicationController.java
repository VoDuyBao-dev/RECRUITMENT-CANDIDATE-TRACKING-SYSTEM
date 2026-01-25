package com.example.RecruitmentCandidateTracking.controller.hr;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.PageResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.ChangeStageRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.ApplicationResponse;
import com.example.RecruitmentCandidateTracking.enums.PipelineStage;
import com.example.RecruitmentCandidateTracking.services.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HR Application Controller
 * Handles application management operations for HR
 */
@RestController
@RequestMapping("/hr/applications")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('HR')")
public class HRApplicationController {

    private final ApplicationService applicationService;

    // Lấy tất cả đơn ứng tuyển cho một vị trí công việc
    @GetMapping("/job/{jobId}")
    public ApiResponse<PageResponse<ApplicationResponse>> getApplicationsByJob(@PathVariable Long jobId, @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size ) {
        // log.info("GET /api/v1/hr/applications/job/{} - Fetching applications for
        // job", jobId);

        PageResponse<ApplicationResponse> applications = applicationService.getApplicationsByJob(jobId, page, size);

        return ApiResponse.<PageResponse<ApplicationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(String.format("Retrieved %d applications for job successfully", applications.getItems().size()))
                .result(applications)
                .build();
    }

    // Lấy tất cả đơn ứng tuyển đã nộp và có phân trang mặc địnhgồm 10 bản ghi mỗi trang
    @GetMapping
    public ApiResponse<PageResponse<ApplicationResponse>> getAllApplications(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size 
    ) {
        // Gọi Service
        PageResponse<ApplicationResponse> pageResponse = applicationService.getAllApplications(page, size);

        // Trả về kết quả
        return ApiResponse.<PageResponse<ApplicationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Retrieved all applications successfully")
                .result(pageResponse)
                .build();
    }

    // Lấy tất cả đơn ứng tuyển theo giai đoạn trong quy trình tuyển dụng
    @GetMapping("/stage/{stage}")
    public ApiResponse<PageResponse<ApplicationResponse>> getApplicationsByStage(@PathVariable PipelineStage stage, @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size ) {
        // log.info("GET /api/v1/hr/applications/stage/{} - Fetching applications by
        // stage", stage);

        PageResponse<ApplicationResponse> applications = applicationService.getApplicationsByStage(stage, page, size);

        return ApiResponse.<PageResponse<ApplicationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(String.format("Retrieved %d applications with stage %s", applications.getItems().size(), stage))
                .result(applications)
                .build();
    }

    // Lấy chi tiết đơn ứng tuyển
    @GetMapping("/{id}")
    public ApiResponse<ApplicationResponse> getApplicationById(@PathVariable Long id) {
        // log.info("GET /api/v1/hr/applications/{} - Fetching application details",
        // id);

        ApplicationResponse response = applicationService.getApplicationById(id);

        return ApiResponse.<ApplicationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Application retrieved successfully")
                .result(response)
                .build();
    }

    // Thay đổi giai đoạn trong quy trình tuyển dụng của đơn ứng tuyển
    @PutMapping("/{id}/stage")
    public ApiResponse<ApplicationResponse> changePipelineStage(
            @PathVariable Long id,
            @Valid @RequestBody ChangeStageRequest request) {
        // log.info("PUT /api/v1/hr/applications/{}/stage - Changing stage to: {}", id,
        // request.getNewStage());

        PipelineStage newStage = PipelineStage.valueOf(request.getNewStage().toUpperCase());
        ApplicationResponse response = applicationService.changePipelineStage(id, newStage);

        return ApiResponse.<ApplicationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Pipeline stage updated successfully")
                .result(response)
                .build();
    }
}