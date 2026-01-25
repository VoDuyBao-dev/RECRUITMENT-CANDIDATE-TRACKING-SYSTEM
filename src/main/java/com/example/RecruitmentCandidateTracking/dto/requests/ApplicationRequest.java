package com.example.RecruitmentCandidateTracking.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {
    
    @NotNull(message = "Job ID is required")
    private Long jobId;
    
    @NotBlank(message = "Resume/CV URL is required")
    @Size(max = 500, message = "Resume path cannot exceed 500 characters")
    private String resumePath;
    
    @Size(max = 2000, message = "Cover letter cannot exceed 2000 characters")
    private String coverLetter; // Optional
}