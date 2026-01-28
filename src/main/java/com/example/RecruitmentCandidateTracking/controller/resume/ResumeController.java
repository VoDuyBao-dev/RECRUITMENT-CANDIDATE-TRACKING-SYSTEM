package com.example.RecruitmentCandidateTracking.controller.resume;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.services.ApplicationService;
import com.example.RecruitmentCandidateTracking.services.GoogleDriveService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resume")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ResumeController {

    ApplicationService applicationService;
//    GoogleDriveService googleDriveService;

    @GetMapping("/{id}/resume-link")
    public ApiResponse<String> getResumeLink(@PathVariable Long id) {
        String viewLink = applicationService.getResumeViewLink(id);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Lấy link xem resume thành công")
                .result(viewLink)
                .build();
    }

//    @PostMapping("/testConnect")
//    public ApiResponse<Void> testConnect() {
//
//        googleDriveService.testDriveConnection();
//        return ApiResponse.<Void>builder()
//                .code(200)
//                .message("Nộp đơn ứng tuyển thành công")
//
//                .build();
//    }
}
