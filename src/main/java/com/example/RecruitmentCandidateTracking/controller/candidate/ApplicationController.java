package com.example.RecruitmentCandidateTracking.controller.candidate;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.ApplicationResponse;
import com.example.RecruitmentCandidateTracking.services.ApplicationService;
import com.example.RecruitmentCandidateTracking.services.GoogleDriveService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
   GoogleDriveService googleDriveService;

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

//    @GetMapping("/my-applications")
//    public ApiResponse<List<ApplicationResponse>> getMyApplications(@AuthenticationPrincipal Jwt jwt) {
//        String email = jwt.getSubject();
//        List<ApplicationResponse> applications = applicationService.getCandidateApplications(email);
//        return ApiResponse.<List<ApplicationResponse>>builder()
//                .code(200)
//                .message("Lấy danh sách đơn ứng tuyển thành công")
//                .result(applications)
//                .build();
//    }

    @GetMapping("/{id}/resume-link")
    public ApiResponse<String> getResumeLink(@PathVariable Long id) {
        String viewLink = applicationService.getResumeViewLink(id);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Lấy link xem resume thành công")
                .result(viewLink)
                .build();
    }

    @PostMapping("/testConnect")
    public ApiResponse<Void> testConnect() {

        googleDriveService.testDriveConnection();
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Nộp đơn ứng tuyển thành công")

                .build();
    }
}