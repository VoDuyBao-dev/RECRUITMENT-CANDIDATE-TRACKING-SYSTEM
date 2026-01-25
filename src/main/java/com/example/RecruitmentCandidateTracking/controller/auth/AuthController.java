package com.example.RecruitmentCandidateTracking.controller.auth;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.AuthenticationRequest;
import com.example.RecruitmentCandidateTracking.dto.requests.CandidateRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.AuthenticationResponse;
import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.services.AuthenticationService;
import com.example.RecruitmentCandidateTracking.services.UserService;
import com.example.RecruitmentCandidateTracking.services.VerificationTokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    UserService userService;
    VerificationTokenService verificationTokenService;
    AuthenticationService authenticationService;

    @PostMapping("/register/candidate")
    public ApiResponse<User> register(@RequestBody CandidateRequest candidateRequest) {
        log.info("Register candidate request: {}", candidateRequest);
        User user = userService.createUserCandidate(candidateRequest);
       return ApiResponse.<User>builder()
               .code(200)
               .message("Đăng ký thành công. Vui lòng xác thực email để kích hoạt tài khoản.")
               .build();
    }

    @GetMapping("/verify-email")
    public void verifyEmail(
            @RequestParam String token,
            HttpServletResponse response) throws IOException {

        try {
            verificationTokenService.verifyAccount(token);
            response.sendRedirect("http://localhost:3000/RecruitmentCandidateTracking/login?verified=true");

        } catch (AppException ex) {
            response.sendRedirect("http://localhost:3000/RecruitmentCandidateTracking/login?verified=false&code=" + ex.getErrorCode().name());
        }
        catch (Exception ex) {
            log.error("Lỗi khi xác thực tài khoản", ex);
            response.sendRedirect("http://localhost:3000/RecruitmentCandidateTracking/login?verified=false&code=AUTHENTICATION_ERROR");
        }

    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("Đăng nhập thành công")
                .result(authenticationResponse)
                .build();

    }


}
