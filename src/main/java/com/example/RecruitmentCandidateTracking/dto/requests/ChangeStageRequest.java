package com.example.RecruitmentCandidateTracking.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStageRequest {
    
    @NotBlank(message = "New stage is required")
    @Pattern(
        regexp = "APPLIED|SCREENING|INTERVIEWING|OFFERED|REJECTED|WITHDRAWN|HIRED",
        message = "Invalid pipeline stage"
    )
    private String newStage;
    
    private String note; // Optional note for stage change
}