package com.example.RecruitmentCandidateTracking.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.RecruitmentCandidateTracking.enums.ERole;
import com.example.RecruitmentCandidateTracking.enums.UserStatus;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private UserStatus status = UserStatus.ACTIVE;

    // Dùng để xác định tài khoản có được phép đăng nhập không
    private Boolean enabled = false;

    private LocalDateTime activationExpiryTime;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;

    @PrePersist
    public void onCreate() {
        if (this.activationExpiryTime == null) {
            this.activationExpiryTime = LocalDateTime.now().plusMinutes(5);
        }
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(this.enabled)
                && this.status == UserStatus.ACTIVE;
    }

    public boolean hasRole(ERole role) {
        return this.roles != null && this.roles.contains(role.name());
    }

}
