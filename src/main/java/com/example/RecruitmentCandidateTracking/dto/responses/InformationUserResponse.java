package com.example.RecruitmentCandidateTracking.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformationUserResponse {
    String fullName;
    String email;

}
