package com.example.RecruitmentCandidateTracking.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import jakarta.persistence.*;
import com.example.RecruitmentCandidateTracking.enums.InterviewType;

@Entity
@Table(name = "interviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    // ManyToMany để hỗ trợ "Hội đồng phỏng vấn" (Nhiều người)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "interview_interviewers", // 1. Tên bảng trung gian sẽ được tạo ra
            joinColumns = @JoinColumn(name = "interview_id"), // 2. Cột giữ khóa ngoại trỏ về bảng hiện tại (Interview)
            inverseJoinColumns = @JoinColumn(name = "user_id") // 3. Cột giữ khóa ngoại trỏ về phía bên kia (User)
    )
    @Builder.Default
    private Set<User> interviewers = new HashSet<>();

    // thông tin chi tiết vòng phỏng vấn //

    @Column(name = "round_number", nullable = false)
    private Integer roundNumber; // Vòng 1, Vòng 2...

    @Column(name = "round_name")
    private String roundName; // VD: "Technical Interview", "Culture Fit"

    @Column(nullable = false)
    private LocalDateTime scheduledTime; // Thời gian bắt đầu

    @Column(nullable = false)
    private LocalDateTime endTime; // Thời gian kết thúc (để check trùng lịch)

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_type", nullable = false, length = 20)
    private InterviewType interviewType;

    
}
