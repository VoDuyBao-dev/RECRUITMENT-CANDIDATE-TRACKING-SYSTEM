package com.example.RecruitmentCandidateTracking.dto.responses;

import com.example.RecruitmentCandidateTracking.enums.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPositionResponse {
    
    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String benefits;
    private Integer quantity;
    private BigDecimal basicSalary;
    private String address;
    private LocalDate startDate;
    private LocalDate deadline;
    private JobStatus status;
    
    // Audit fields
    private LocalDateTime createdAt;
    private LocalDate updatedAt;
    private String createdByUsername; // Username của người tạo
    private Long createdByUserId;     // ID của người tạo
    private String branchName;
}