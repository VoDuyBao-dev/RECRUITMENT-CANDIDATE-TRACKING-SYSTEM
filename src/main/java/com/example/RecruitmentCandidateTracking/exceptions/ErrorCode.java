package com.example.RecruitmentCandidateTracking.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

// Định nghĩa các mã lỗi và message của lỗi
@Getter
public enum ErrorCode {
        UNCATEGORIZED_EXCEPTION(9999, "uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR), // exception chưa được
                                                                                                // đinhj
                                                                                                // nghĩa hoặc loại ex
                                                                                                // chưa
                                                                                                // bắt

        INVALID_TOKEN(9998, "Token invalid", HttpStatus.INTERNAL_SERVER_ERROR),
        STACK_OVERFLOW(9997, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
        MISSING_PARAMETER(9996, "Missing required parameter", HttpStatus.BAD_REQUEST),
        METHOD_INVALID(9995, "Method is not supported", HttpStatus.BAD_REQUEST),

        USER_EXISTED(1002, "user existed", HttpStatus.BAD_REQUEST),
        USER_NOT_EXISTED(1005, "user not existed", HttpStatus.NOT_FOUND),
        SAVE_USER_FAILED(1014, "save user failed", HttpStatus.INTERNAL_SERVER_ERROR),
        UPDATE_USER_FAILED(1015, "update user failed", HttpStatus.INTERNAL_SERVER_ERROR),

        // Xử lí nhập vào sai key Enum
        INVALID_KEY(1001, "invalid message key", HttpStatus.BAD_REQUEST),
        UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
        UNAUTHORIZED(1017, "you do not have permission", HttpStatus.FORBIDDEN),

        // loi login
        INVALID_CREDENTIALS(1012, "invalid credentials", HttpStatus.UNAUTHORIZED),
        PASSWORDS_DO_NOT_MATCH(1013, "Password and confirm password do not match", HttpStatus.BAD_REQUEST),

        // xác thực tài khoản
        TOKEN_EXPIRED(2000, "token expired", HttpStatus.BAD_REQUEST),
        TOKEN_ALREADY_USED(2001, "token already used", HttpStatus.BAD_REQUEST),
        TOKEN_NOT_FOUND(2002, "token not found", HttpStatus.BAD_REQUEST),
        // ACCOUNT
        ACCOUNT_NOT_ACTIVATED(2003, "account not activated", HttpStatus.BAD_REQUEST),
        ACCOUNT_DISABLED(2004, "account disabled", HttpStatus.BAD_REQUEST),
        ACCOUNT_INACTIVE(2005, "account locked", HttpStatus.BAD_REQUEST),
        UNKNOWN_USER_STATUS(2006, "unknown user status", HttpStatus.INTERNAL_SERVER_ERROR),

        // ==================== USER ERROR CODES ====================
        USER_NOT_FOUND(2001, "User not found", HttpStatus.NOT_FOUND),
        USER_ALREADY_EXISTS(2002, "User already exists", HttpStatus.CONFLICT),
        INVALID_USERNAME(2003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
        INVALID_PASSWORD(2004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),

        // ==================== JOB POSITION ERROR CODES ====================
        JOB_NOT_FOUND(3001, "Job position not found", HttpStatus.NOT_FOUND),
        JOB_ALREADY_CLOSED(3002, "Job position is already closed and cannot be updated", HttpStatus.BAD_REQUEST),
        JOB_TITLE_ALREADY_EXISTS(3003, "Job position with this title already exists", HttpStatus.CONFLICT),
        INVALID_DEADLINE(3004, "Job deadline must be after start date", HttpStatus.BAD_REQUEST),
        INVALID_START_DATE(3005, "Job start date cannot be in the past", HttpStatus.BAD_REQUEST),
        INVALID_JOB_STATUS(3006, "Invalid job status. Must be OPEN, DRAFT, or CLOSED", HttpStatus.BAD_REQUEST),
        JOB_HAS_APPLICATIONS(3007, "Cannot delete job position with existing applications", HttpStatus.BAD_REQUEST),
        JOB_STATUS_NOT_ALLOWED(3008, "Job status change not allowed", HttpStatus.BAD_REQUEST),
        JOB_STATUS_NOT_CHANGED(3009, "Job status is already set to the specified value", HttpStatus.BAD_REQUEST),

        // ==================== APPLICATION ERROR CODES ====================
        APPLICATION_NOT_FOUND(4001, "Application not found", HttpStatus.NOT_FOUND),
        APPLICATION_ALREADY_EXISTS(4002, "You have already applied for this job", HttpStatus.CONFLICT),

        // ==================== INTERVIEW ERROR CODES ====================
        INTERVIEW_NOT_FOUND(5001, "Interview schedule not found", HttpStatus.NOT_FOUND),
        INVALID_INTERVIEW_TIME(5002, "Invalid interview time", HttpStatus.BAD_REQUEST),

        ALREADY_APPLIED(4002, "You have already applied for this job", HttpStatus.CONFLICT),
        JOB_NOT_OPEN(4003, "This job position is not open for applications", HttpStatus.BAD_REQUEST),
        JOB_DEADLINE_PASSED(4004, "The application deadline for this job has passed", HttpStatus.BAD_REQUEST),
        INVALID_STAGE_TRANSITION(4005, "Invalid pipeline stage transition", HttpStatus.BAD_REQUEST),
        CANNOT_CHANGE_HIRED_STATUS(4006, "Cannot change status of hired applications", HttpStatus.BAD_REQUEST),

        INTERVIEWER_TIME_CONFLICT(5003, "Interviewer has a conflicting interview at this time", HttpStatus.CONFLICT),
        CANNOT_SCHEDULE_INTERVIEW(5004, "Cannot schedule interview for this application status",
                        HttpStatus.BAD_REQUEST),
        CANNOT_UPDATE_EVALUATED_INTERVIEW(5005, "Cannot update interview that has been evaluated",
                        HttpStatus.BAD_REQUEST),
        NOT_ASSIGNED_INTERVIEWER(5006, "You are not assigned as interviewer for this interview", HttpStatus.FORBIDDEN),
        EVALUATION_ALREADY_SUBMITTED(5007, "You have already submitted evaluation for this interview",
                        HttpStatus.CONFLICT);

        ;

        private int code;
        private String message;
        private HttpStatusCode httpStatusCode;

        ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
                this.code = code;
                this.message = message;
                this.httpStatusCode = httpStatusCode;
        }

}
