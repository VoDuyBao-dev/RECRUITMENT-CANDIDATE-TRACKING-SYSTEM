package com.example.RecruitmentCandidateTracking.dto.requests;

import com.example.RecruitmentCandidateTracking.enums.ContractType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOfferRequest {

    @NotNull(message = "APPLICATIONID_REQUIRED")
    Long applicationId;

    @NotNull(message = "START_WORK_DATE_REQUIRED")
    @FutureOrPresent(message = "START_WORK_DATE_INVALID")
    LocalDate startWorkDate;

    @NotNull(message = "BASIC_SALARY_REQUIRED")
    BigDecimal basicSalary;

    BigDecimal probationSalary;

    @NotNull(message = "CONTRACR_TYPE_REQUIRED")
    ContractType contractType;
}