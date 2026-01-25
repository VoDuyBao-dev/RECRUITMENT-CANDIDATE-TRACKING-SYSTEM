package com.example.RecruitmentCandidateTracking.controller.publics;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.JobPositionResponse;
import com.example.RecruitmentCandidateTracking.services.JobPositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Public Job Controller
 * Anyone can view public job listings (no authentication required)
 */
@RestController
@RequestMapping("/api/v1/public/jobs")
@RequiredArgsConstructor
@Slf4j
public class PublicJobController {
    
    private final JobPositionService jobPositionService;
    
// Lấy toàn bộ danh sách công viêc đang tuyển dụng (các công việc có trạng thái OPEN và chưa hết hạn hồ sơ)
    @GetMapping
    public ApiResponse<List<JobPositionResponse>> getAllPublicJobs() {
        log.info("GET /api/v1/public/jobs - Fetching all public job positions");
        
        List<JobPositionResponse> jobs = jobPositionService.getAllPublicJobs();
        
        return ApiResponse.<List<JobPositionResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(String.format("Retrieved %d job positions successfully", jobs.size()))
                .result(jobs)
                .build();
    }
    
// Lấy chi tiết một công việc 
    @GetMapping("/{id}")
    public ApiResponse<JobPositionResponse> getJobById(@PathVariable Long id) {
        log.info("GET /api/v1/public/jobs/{} - Fetching job position details", id);
        
        JobPositionResponse response = jobPositionService.getJobById(id);
        
        return ApiResponse.<JobPositionResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Job position retrieved successfully")
                .result(response)
                .build();
    }
}