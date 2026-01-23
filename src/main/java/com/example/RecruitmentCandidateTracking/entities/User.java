package com.example.RecruitmentCandidateTracking.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import com.example.RecruitmentCandidateTracking.enums.UserStatus;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private UserStatus status = UserStatus.ACTIVE;
    
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER) // EAGER: Tải user thì tải luôn role
    @JoinTable(name = "user_roles", // Tên của bảng trung gian thứ 3
            joinColumns = @JoinColumn(name = "user_id"), // Khóa ngoại trỏ về bảng User
            inverseJoinColumns = @JoinColumn(name = "role_id") // Khóa ngoại trỏ về bảng Role
    )
    private Set<Role> roles = new HashSet<>();
    
}
