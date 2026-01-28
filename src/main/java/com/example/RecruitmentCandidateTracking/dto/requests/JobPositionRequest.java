package com.example.RecruitmentCandidateTracking.dto.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPositionRequest {

    @NotBlank(message = "Job title is required")
    @Size(min = 3, max = 250, message = "Job title must be between 3 and 250 characters")
    private String title;

    @NotBlank(message = "Job description is required")
    private String description;

    @NotBlank(message = "Job requirements are required")
    private String requirements;

    private String benefits; // Optional

    // Tên chi nhánh
    @NotBlank(message = "Branch name is required")
    private String branchName;

    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 100, message = "Quantity cannot exceed 100")
    private Integer quantity;

    @NotNull(message = "Basic salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Basic salary must be greater than 0")
    private BigDecimal basicSalary;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 500, message = "Address must be between 5 and 500 characters")
    private String address;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "Deadline is required")
    private LocalDate deadline;

    @Pattern(regexp = "OPEN|DRAFT|CLOSED", message = "Status must be OPEN, DRAFT, or CLOSED")
    private String status; // Optional, defaults to OPEN

}