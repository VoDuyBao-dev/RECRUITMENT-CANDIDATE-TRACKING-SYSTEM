package com.example.RecruitmentCandidateTracking.dto.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewersResponse {
    private Long id;
    private String fullName;
    private String email;
}
