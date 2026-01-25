package com.example.RecruitmentCandidateTracking.dto.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationRequest {
    
    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 100, message = "Score cannot exceed 100")
    private int score;
    
    @NotBlank(message = "Comment is required")
    @Size(min = 10,message = "Comment must be more than 10 characters")
    private String comment;
}