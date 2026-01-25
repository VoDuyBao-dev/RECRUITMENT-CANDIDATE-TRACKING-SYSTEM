package com.example.RecruitmentCandidateTracking.controller.user;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.CandidateRequest;
import com.example.RecruitmentCandidateTracking.entities.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/user")
public class UserController {

    @PatchMapping("/update-user")
    public ApiResponse<Void> register() {

        return ApiResponse.<Void>builder()
                .code(200)
                .message("tesst bảo mật tokenm")
                .build();
    }
}
