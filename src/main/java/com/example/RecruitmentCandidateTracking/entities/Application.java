package com.example.RecruitmentCandidateTracking.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;
import jakarta.persistence.*;
import com.example.RecruitmentCandidateTracking.enums.PipelineStage;

@Entity
@Table(name = "applications", uniqueConstraints = {
        // Ràng buộc: Một ứng viên không được nộp cùng 1 job 2 lần (tránh spam)
        @UniqueConstraint(columnNames = { "candidate_id", "job_id" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private User candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private JobPosition job;

    @Column(name = "resume_path", nullable = false, length = 500)
    private String resumePath;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "current_stage", nullable = false, length = 20)
    private PipelineStage currentStage = PipelineStage.APPLIED;

    @Column(name = "applied_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime appliedDate;

    // mappedBy = "application": Báo hiệu field "application" bên bảng Interview mới là chủ sở hữu khóa ngoại.
    // cascade = CascadeType.ALL: Nếu xóa Application -> Xóa luôn tất cả lịch Interview của nó.
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Interview> interviews = new ArrayList<>();
}
