package com.example.RecruitmentCandidateTracking.controller.candidate;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.UserUpdateInformationRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.ApplicationResponse;
import com.example.RecruitmentCandidateTracking.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate/update")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UpdateController {
    UserService userService;

    @PatchMapping("/info")
    public ApiResponse<Void> updateCandidateInfo(@RequestBody UserUpdateInformationRequest user) {
        userService.updateUser(user);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Chỉnh sửa thông tin thành công")
                .build();
    }


}
