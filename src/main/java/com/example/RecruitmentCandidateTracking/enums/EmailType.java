package com.example.RecruitmentCandidateTracking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EmailType {

    VERIFY_ACCOUNT(
            "Activate your account",
            "email/verify_account_email"
    ),

    RESET_PASSWORD(
            "Reset your password",
            "email/reset_password_email"
    );

    private final String subject;
    private final String template;


}
