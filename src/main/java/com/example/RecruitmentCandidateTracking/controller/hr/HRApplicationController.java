package com.example.RecruitmentCandidateTracking.controller.hr;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
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
    public ApiResponse<List<ApplicationResponse>> getApplicationsByJob(@PathVariable Long jobId) {
        // log.info("GET /api/v1/hr/applications/job/{} - Fetching applications for job", jobId);
        
        List<ApplicationResponse> applications = applicationService.getApplicationsByJob(jobId);
        
        return ApiResponse.<List<ApplicationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(String.format("Retrieved %d applications for job successfully", applications.size()))
                .result(applications)
                .build();
    }
    
// Lấy tất cả đơn ứng tuyển theo giai đoạn trong quy trình tuyển dụng 
    @GetMapping("/stage/{stage}")
    public ApiResponse<List<ApplicationResponse>> getApplicationsByStage(@PathVariable PipelineStage stage) {
        // log.info("GET /api/v1/hr/applications/stage/{} - Fetching applications by stage", stage);
        
        List<ApplicationResponse> applications = applicationService.getApplicationsByStage(stage);
        
        return ApiResponse.<List<ApplicationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(String.format("Retrieved %d applications with stage %s", applications.size(), stage))
                .result(applications)
                .build();
    }
    
// Lấy chi tiết đơn ứng tuyển 
    @GetMapping("/{id}")
    public ApiResponse<ApplicationResponse> getApplicationById(@PathVariable Long id) {
        // log.info("GET /api/v1/hr/applications/{} - Fetching application details", id);
        
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
        // log.info("PUT /api/v1/hr/applications/{}/stage - Changing stage to: {}", id, request.getNewStage());
        
        PipelineStage newStage = PipelineStage.valueOf(request.getNewStage().toUpperCase());
        ApplicationResponse response = applicationService.changePipelineStage(id, newStage);
        
        return ApiResponse.<ApplicationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Pipeline stage updated successfully")
                .result(response)
                .build();
    }
}