package com.example.RecruitmentCandidateTracking.controller.auth;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.CandidateRequest;
import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.services.UserService;
import com.example.RecruitmentCandidateTracking.services.VerificationTokenService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    UserService userService;
    VerificationTokenService verificationTokenService;

    @PostMapping("/register/candidate")
    public ApiResponse<User> register(@RequestBody CandidateRequest candidateRequest) {
        log.info("Register candidate request: {}", candidateRequest);
        User user = userService.createUserCandidate(candidateRequest);
       return ApiResponse.<User>builder()
               .code(200)
               .message("register candidate succes")
               .result(user)
               .build();
    }

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam String token) {
        verificationTokenService.verifyAccount(token);
        return "verify_success";
    }

}
