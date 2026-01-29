package com.example.RecruitmentCandidateTracking.controller.candidate;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.ApplicationResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.CandidateAppliedJobResponse;
import com.example.RecruitmentCandidateTracking.services.ApplicationService;
import com.example.RecruitmentCandidateTracking.services.CandidateApplicationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/candidate/applications")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {

   ApplicationService applicationService;

    CandidateApplicationService candidateApplicationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ApplicationResponse> submitApplication(
            @RequestParam("jobId") Long jobId,
            @RequestParam("resume") MultipartFile resumeFile) {

        ApplicationResponse response = applicationService.submitApplication(jobId, resumeFile);
        return ApiResponse.<ApplicationResponse>builder()
                .code(200)
                .message("Nộp đơn ứng tuyển thành công")
                .result(response)
                .build();
    }

    @GetMapping("/applied-jobs")
    public ApiResponse<List<CandidateAppliedJobResponse>> getAppliedJobs() {

        List<CandidateAppliedJobResponse> result = candidateApplicationService.getAppliedJobs();

        return ApiResponse.<List<CandidateAppliedJobResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách công việc đã ứng tuyển thành công")
                .result(result)
                .build();
    }






}