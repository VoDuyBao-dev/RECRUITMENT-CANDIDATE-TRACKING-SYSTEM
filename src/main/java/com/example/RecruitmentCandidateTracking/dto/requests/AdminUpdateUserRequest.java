package com.example.RecruitmentCandidateTracking.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateUserRequest {

    @NotBlank(message = "Full name must not be blank")
    @Size(max = 255)
    private String fullName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Roles must not be empty")
    private Set<String> roles;
}
