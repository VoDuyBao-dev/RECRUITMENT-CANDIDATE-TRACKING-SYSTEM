package com.example.RecruitmentCandidateTracking.controller.interview;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.InterviewEvaluationDetailResponse;
import com.example.RecruitmentCandidateTracking.services.InterviewEvaluationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interviews")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class InterviewController {
    InterviewEvaluationService interviewEvaluationService;

    @GetMapping("/{interviewId}/evaluations")
    public ApiResponse<InterviewEvaluationDetailResponse> getInterviewEvaluationDetail(
            @PathVariable Long interviewId
    ) {

        InterviewEvaluationDetailResponse response = interviewEvaluationService.getInterviewEvaluationDetail(interviewId);

        return ApiResponse.<InterviewEvaluationDetailResponse>builder()
                .code(200)
                .message("Lấy chi tiết đánh giá phỏng vấn thành công")
                .result(response)
                .build();
    }

}
