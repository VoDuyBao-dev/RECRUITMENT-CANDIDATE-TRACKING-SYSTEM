package com.example.RecruitmentCandidateTracking.controller.candidate;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.PageResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.JobPositionResponse;
import com.example.RecruitmentCandidateTracking.services.JobPositionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate/jobs")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CandidateJobController {
    JobPositionService jobPositionService;

    // Lấy chi tiết một công việc
    @GetMapping("/{id}")
    public ApiResponse<JobPositionResponse> candidateGetJobById(@PathVariable Long id) {

        JobPositionResponse response = jobPositionService.getJobById(id);

        return ApiResponse.<JobPositionResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin công việc thành công")
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


    @GetMapping("/search")
    public ApiResponse<PageResponse<JobPositionResponse>> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<JobPositionResponse> result = jobPositionService.searchJobsForCandidate(keyword, page, size);

        return ApiResponse.<PageResponse<JobPositionResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(String.format("Found %d job positions", result.getItems().size()))
                .result(result)
                .build();
    }


}
