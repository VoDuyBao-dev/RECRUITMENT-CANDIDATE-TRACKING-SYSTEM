package com.example.RecruitmentCandidateTracking.dto.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRequest {
    
    @NotNull(message = "Application ID is required")
    private Long applicationId;
    
    @NotEmpty(message = "At least one interviewer is required")
    private Set<Long> interviewerIds; // List of interviewer user IDs
    
    @NotNull(message = "Round number is required")
    @Min(value = 1, message = "Round number must be at least 1")
    private Integer roundNumber;
    
    @Size(max = 200, message = "Round name cannot exceed 200 characters")
    private String roundName; // e.g., "Technical Interview", "Culture Fit"
    
    @NotNull(message = "Scheduled time is required")
    @Future(message = "Scheduled time must be in the future")
    private LocalDateTime scheduledTime;
    
    @NotNull(message = "End time is required")
    private LocalDateTime endTime;
    
    @NotBlank(message = "Interview type is required")
    @Pattern(regexp = "ONLINE|OFFLINE", message = "Interview type must be ONLINE or OFFLINE")
    private String interviewType;
}