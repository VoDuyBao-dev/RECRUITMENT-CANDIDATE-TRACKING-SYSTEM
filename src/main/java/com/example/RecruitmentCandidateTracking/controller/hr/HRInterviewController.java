package com.example.RecruitmentCandidateTracking.controller.hr;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.InterviewRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.EvaluationResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.InterviewResponse;
import com.example.RecruitmentCandidateTracking.services.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/hr/interviews")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('HR')")
public class HRInterviewController {
    
    private final InterviewService interviewService;
    
// tạo lịch phỏng vấn 
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<InterviewResponse> scheduleInterview(@Valid @RequestBody InterviewRequest request) {
        log.info("POST /api/v1/hr/interviews - Scheduling interview for application ID: {}", 
                 request.getApplicationId());
        
        InterviewResponse response = interviewService.scheduleInterview(request);
        
        return ApiResponse.<InterviewResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Interview scheduled successfully")
                .result(response)
                .build();
    }
    
// điều chỉnh lịch phỏng vấn 
    @PutMapping("/{id}")
    public ApiResponse<InterviewResponse> updateInterview(
            @PathVariable Long id,
            @Valid @RequestBody InterviewRequest request) {
        log.info("PUT /api/v1/hr/interviews/{} - Updating interview schedule", id);
        
        InterviewResponse response = interviewService.updateInterview(id, request);
        
        return ApiResponse.<InterviewResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Interview schedule updated successfully")
                .result(response)
                .build();
    }
    
// Lấy tất cả lịch phỏng vấn cho một đơn ứng tuyển
    @GetMapping("/application/{applicationId}")
    public ApiResponse<List<InterviewResponse>> getInterviewsByApplication(
            @PathVariable Long applicationId) {
        log.info("GET /api/v1/hr/interviews/application/{} - Fetching interviews", applicationId);
        
        List<InterviewResponse> interviews = interviewService.getInterviewsByApplication(applicationId);
        
        return ApiResponse.<List<InterviewResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(String.format("Retrieved %d interviews successfully", interviews.size()))
                .result(interviews)
                .build();
    }
    
// Lấy tất cả đánh giá cho một cuộc phỏng vấn
    @GetMapping("/{id}/evaluations")
    public ApiResponse<List<EvaluationResponse>> getEvaluationsByInterview(@PathVariable Long id) {
        log.info("GET /api/v1/hr/interviews/{}/evaluations - Fetching evaluations", id);
        
        List<EvaluationResponse> evaluations = interviewService.getEvaluationsByInterview(id);
        
        return ApiResponse.<List<EvaluationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(String.format("Retrieved %d evaluations successfully", evaluations.size()))
                .result(evaluations)
                .build();
    }
}