package com.example.RecruitmentCandidateTracking.entities;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import com.example.RecruitmentCandidateTracking.enums.ContractType;
@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false, unique = true)
    private Application application;

    // Ai là người DUYỆT offer này? (Sếp/Manager/HR????)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Column(name = "start_work_date", nullable = false)
    private LocalDate startWorkDate; // Ngày bắt đầu đi làm (Quan trọng)

    // @Column(name = "offer_expiration_date")
    // private LocalDate expirationDate; // Hạn chót ứng viên phải trả lời (tránh ngâm offer)

    @Column(name = "basic_salary", nullable = false)
    private BigDecimal basicSalary; // Lương chính thức (Gross)

    @Column(name = "probation_salary")
    private BigDecimal probationSalary; // Lương thử việc (Thường là 85% lương chính)

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type")
    private ContractType contractType; // FULL_TIME, PART_TIME, INTERNSHIP
}
