package com.example.RecruitmentCandidateTracking.controller.interviewer;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.EvaluationRequest;
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

/**
 * Interviewer Controller
 * Handles interview evaluation operations for interviewers
 */
@RestController
@RequestMapping("/api/v1/interviewer/interviews")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAnyRole('INTERVIEWER', 'HR')")
public class InterviewerController {
    
    private final InterviewService interviewService;
    
// lấy lịch phỏng vấn của một interview cụ thể 
    @GetMapping
    public ApiResponse<List<InterviewResponse>> getMySchedule() {
        log.info("GET /api/v1/interviewer/interviews - Fetching my interview schedule");
        
        List<InterviewResponse> interviews = interviewService.getMySchedule();
        
        return ApiResponse.<List<InterviewResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(String.format("Retrieved %d upcoming interviews successfully", interviews.size()))
                .result(interviews)
                .build();
    }
    
// đánh giá sau phỏng vấn
    @PostMapping("/{id}/evaluate")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EvaluationResponse> submitEvaluation(
            @PathVariable Long id,
            @Valid @RequestBody EvaluationRequest request) {
        log.info("POST /api/v1/interviewer/interviews/{}/evaluate - Submitting evaluation", id);
        
        EvaluationResponse response = interviewService.submitEvaluation(id, request);
        
        return ApiResponse.<EvaluationResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Evaluation submitted successfully")
                .result(response)
                .build();
    }
}