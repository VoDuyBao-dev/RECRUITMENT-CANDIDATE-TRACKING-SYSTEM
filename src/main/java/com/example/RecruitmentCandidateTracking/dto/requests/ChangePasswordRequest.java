package com.example.RecruitmentCandidateTracking.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "OLD_PASSWORD_REQUIRED")
    @Size(min = 6, max = 30, message = "PASSWORD_TOO_SHORT")
    String oldPassword;
    @NotBlank(message = "NEW_PASSWORD_REQUIRED")
    @Size(min = 6, max = 30, message = "PASSWORD_TOO_SHORT")
    String newPassword;
    @NotBlank(message = "CONFIRM_NEW_PASSWORD_REQUIRED")
    @Size(min = 6, max = 30, message = "PASSWORD_TOO_SHORT")
    String confirmNewPassword;
}
