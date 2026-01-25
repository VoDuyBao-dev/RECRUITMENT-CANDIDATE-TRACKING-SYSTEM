package com.example.RecruitmentCandidateTracking.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "EMAIL_INVALID"
    )
    private String email;
    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 6, max = 30, message = "PASSWORD_TOO_SHORT")
    private String password;

}
