package com.example.RecruitmentCandidateTracking.entities;


import com.example.RecruitmentCandidateTracking.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles; // ADMIN, TUTOR, PARENT

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "avatar_image", length = 255)
    private String avatarImage;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private UserStatus status = UserStatus.ACTIVE; // ACTIVE, INACTIVE, LOCKED, PENDING_ACTIVATION

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime activationExpiryTime;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    // Dùng để xác định tài khoản có được phép đăng nhập không
    private Boolean enabled = false;

    @PrePersist
    public void onCreate() {
        if (this.activationExpiryTime == null) {
            this.activationExpiryTime = LocalDateTime.now().plusMinutes(3);
        }
    }

}
