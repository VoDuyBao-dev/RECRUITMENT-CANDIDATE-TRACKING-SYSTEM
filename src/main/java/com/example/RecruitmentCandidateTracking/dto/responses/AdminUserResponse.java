package com.example.RecruitmentCandidateTracking.dto.responses;

import com.example.RecruitmentCandidateTracking.enums.UserStatus;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserResponse {

    private Long id;
    private String fullName;
    private String email;
    private Set<String> roles;
    private UserStatus status;
    private Boolean enabled;
    private String createdAt;
}
