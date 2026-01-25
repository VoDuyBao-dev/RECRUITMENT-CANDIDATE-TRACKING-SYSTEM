package com.example.RecruitmentCandidateTracking.entities;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.example.RecruitmentCandidateTracking.enums.JobStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "job_positions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPosition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long id;

    @Column(nullable = false, length = 250)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) 
    private String description; 

    @Column(columnDefinition = "TEXT", nullable = false)
    private String requirements; 

    @Column(columnDefinition = "TEXT") 
    private String benefits; // Có thể null (tùy chính sách cty)

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private JobStatus status = JobStatus.OPEN; 

    @Column(nullable = false)
    private String address;

    @Column(name = "basic_salary", nullable = false)
    private BigDecimal basicSalary; // Lương cơ bản

    // Dùng LAZY để tối ưu hiệu năng
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "created_by_user_id", nullable = false) // Đặt tên rõ ràng hơn
    private User createdBy;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;   // Ngày bắt đầu nhận hồ sơ

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;   // Hạn chót nộp hồ sơ

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;    // Ngày cập nhật thông tin tuyển dụng gần nhất
    
    @Column(name = "quantity", nullable = false)
    @Builder.Default
    private Integer quantity = 1; // Mặc định tuyển ít nhất 1 người
    
}