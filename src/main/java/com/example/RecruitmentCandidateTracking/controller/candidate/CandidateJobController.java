package com.example.RecruitmentCandidateTracking.controller.candidate;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
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
}
