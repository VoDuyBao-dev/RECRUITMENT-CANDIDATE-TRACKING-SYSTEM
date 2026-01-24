package com.example.RecruitmentCandidateTracking.scheduler;

import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserCleanupScheduler {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 */5 * * * *") // 5 phút
    public void deleteExpiredAccounts() {
        List<User> expiredUsers = userRepository
                .findByEnabledFalseAndActivationExpiryTimeBefore(LocalDateTime.now());

        if (!expiredUsers.isEmpty()) {
            userRepository.deleteAll(expiredUsers);
            log.info("Đã xóa {} tài khoản chưa kích hoạt hết hạn", expiredUsers.size());
        }
    }
}
