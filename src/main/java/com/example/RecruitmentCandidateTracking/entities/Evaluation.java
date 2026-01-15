package com.example.RecruitmentCandidateTracking.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "evaluations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //  Feedback này thuộc về buổi phỏng vấn nào?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    // Ai là người viết đánh giá này?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interviewer_id", nullable = false)
    private User interviewer;

    //  Nội dung đánh giá chi tiết
    @Column(nullable = false)
    private Integer score; // Điểm số (VD: 8/10)

    @Column(columnDefinition = "TEXT")
    private String comment; // Nhận xét chi tiết
}
