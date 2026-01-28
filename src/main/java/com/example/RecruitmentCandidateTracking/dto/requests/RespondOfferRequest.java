package com.example.RecruitmentCandidateTracking.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespondOfferRequest {
    @NotNull(message = "APPLICATIONID_REQUIRED")
    private Long applicationId;

    @NotNull(message = "DECISION_REQUIRED")
    private Boolean accepted; // true = accept, false = reject
}
