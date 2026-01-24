package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.entities.VerificationToken;
import com.example.RecruitmentCandidateTracking.enums.EmailType;
import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.exceptions.ErrorCode;
import com.example.RecruitmentCandidateTracking.repositories.UserRepository;
import com.example.RecruitmentCandidateTracking.repositories.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    // Tạo token
    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void generateAndSendVerifyLink(String email) {

        String token = generateToken();

        VerificationToken verificationToken = VerificationToken.builder()
                .email(email)
                .token(token)
                .used(false)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();

        tokenRepository.save(verificationToken);

        String verifyLink = "http://localhost:8080/RecruitmentCandidateTracking/auth/verify?token=" + token;

        emailService.sendEmail(
                email,
                EmailType.VERIFY_ACCOUNT,
                verifyLink
        );

    }

    public void verifyAccount(String token) {

        VerificationToken verificationToken =
                tokenRepository.findByToken(token).orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_FOUND));;

        if (verificationToken.isUsed()) {
            throw new AppException(ErrorCode.TOKEN_ALREADY_USED);
        }

        if (verificationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }

        // kích hoạt user
        User user = userRepository
                .findByEmail(verificationToken.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setEnabled(true);
        userRepository.save(user);

        verificationToken.setUsed(true);
        tokenRepository.save(verificationToken);

        log.info("Account verified: {}", user.getEmail());
    }
}

