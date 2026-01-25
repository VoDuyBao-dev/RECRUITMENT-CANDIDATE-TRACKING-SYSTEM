package com.example.RecruitmentCandidateTracking.controller.hr;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.PageResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.JobPositionRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.JobPositionResponse;
import com.example.RecruitmentCandidateTracking.enums.JobStatus;
import com.example.RecruitmentCandidateTracking.services.JobPositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * HR Job Management Controller
 * Only HR role can access these endpoints
 */
@RestController
@RequestMapping("/hr/jobs")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('HR')")
public class HRJobController {

    private final JobPositionService jobPositionService;

    // Create a new job position
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<JobPositionResponse> createJob(@Valid @RequestBody JobPositionRequest request) {

        JobPositionResponse response = jobPositionService.createJob(request);

        return ApiResponse.<JobPositionResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Job position created successfully")
                .result(response)
                .build();
    }

    // Update an existing job position
    @PutMapping("/{id}")
    public ApiResponse<JobPositionResponse> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobPositionRequest request) {
        // log.info("PUT /api/v1/hr/jobs/{} - Updating job position", id);

        JobPositionResponse response = jobPositionService.updateJob(id, request);

        return ApiResponse.<JobPositionResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Job position updated successfully")
                .result(response)
                .build();
    }

// Change job status (OPEN / CLOSED / PAUSED)
@PutMapping("/{id}/status/{status}")
public ApiResponse<JobPositionResponse> changeJobStatus(
        @PathVariable Long id,
        @PathVariable JobStatus status) {

    JobPositionResponse response = jobPositionService.changeStatusJob(id, status);

    return ApiResponse.<JobPositionResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Job status updated successfully")
            .result(response)
            .build();
}


    // Lấy tất cả các vị trí công việc đã tạo được sắp xếp theo ngày tạo mới nhất
    @GetMapping
    public ApiResponse<PageResponse<JobPositionResponse>> getAllInternalJobs(@RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size ) {
        // log.info("GET /api/v1/hr/jobs - Fetching all internal job positions");

        PageResponse<JobPositionResponse> jobs = jobPositionService.getAllInternalJobs(page, size);

        return ApiResponse.<PageResponse<JobPositionResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(String.format("Retrieved %d job positions successfully", jobs.getItems().size()))
                .result(jobs)
                .build();
    }

// Lấy chi tiết một công việc
    @GetMapping("/{id}")
    public ApiResponse<JobPositionResponse> getJobById(@PathVariable Long id) {
        // log.info("GET /api/v1/hr/jobs/{} - Fetching job position details", id);

        JobPositionResponse response = jobPositionService.getJobById(id);

        return ApiResponse.<JobPositionResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Job position retrieved successfully")
                .result(response)
                .build();
    }

    // Xóa một công việc
    // @DeleteMapping("/{id}")
    // public ApiResponse<Void> deleteJob(@PathVariable Long id) {
    //     log.info("DELETE /api/v1/hr/jobs/{} - Deleting job position", id);

    //     jobPositionService.deleteJob(id);

    //     return ApiResponse.<Void>builder()
    //             .code(HttpStatus.OK.value())
    //             .message("Job position deleted successfully")
    //             .build();
    // }

}