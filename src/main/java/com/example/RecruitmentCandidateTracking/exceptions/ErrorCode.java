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

//       xác thực tài khoản
        TOKEN_EXPIRED(2000, "token expired", HttpStatus.BAD_REQUEST),
        TOKEN_ALREADY_USED(2001, "token already used", HttpStatus.BAD_REQUEST),
        TOKEN_NOT_FOUND(2002, "token not found", HttpStatus.BAD_REQUEST),
        // ACCOUNT
        ACCOUNT_NOT_ACTIVATED(2003, "account not activated", HttpStatus.BAD_REQUEST),
        ACCOUNT_DISABLED(2004, "account disabled", HttpStatus.BAD_REQUEST),
        ACCOUNT_INACTIVE(2005, "account locked", HttpStatus.BAD_REQUEST),
        UNKNOWN_USER_STATUS(2006, "unknown user status", HttpStatus.INTERNAL_SERVER_ERROR),
        SAVE_INVALIDATED_TOKEN_FAILED(2007, "save invalidated token failed", HttpStatus.INTERNAL_SERVER_ERROR),

        EMAIL_INVALID(2008, "Invalid email format", HttpStatus.BAD_REQUEST),
        PASSWORD_REQUIRED(2009, "Password is required", HttpStatus.BAD_REQUEST),
        PASSWORD_TOO_SHORT(2010, "Password must be between 6 and 30 characters", HttpStatus.BAD_REQUEST),

        // USER / CANDIDATE
        CANDIDATE_NOT_EXISTED(3001, "Candidate does not exist", HttpStatus.NOT_FOUND),

        // JOB POSITION
        JOB_POSITION_NOT_EXISTED(3002, "Job position does not exist", HttpStatus.NOT_FOUND),
        ALREADY_APPLIED_JOB(3003, "You have already applied for this job", HttpStatus.BAD_REQUEST),

        //APPLICATION
        APPLICATION_NOT_EXISTED(3004, "Application does not exist", HttpStatus.NOT_FOUND),

        //  GOOGLE DRIVE
        DRIVE_UPLOAD_FAILED(3501, "Failed to upload resume to Google Drive", HttpStatus.INTERNAL_SERVER_ERROR),
        DRIVE_GET_LINK_FAILED(3502, "Failed to get resume view link", HttpStatus.INTERNAL_SERVER_ERROR),
        DRIVE_DELETE_FAILED(3503, "Failed to delete resume from Google Drive", HttpStatus.INTERNAL_SERVER_ERROR),
        DRIVE_CONNECTION_FAILED(3504, "Drive connection failed", HttpStatus.INTERNAL_SERVER_ERROR),

        //FILE VALIDATION
        FILE_REQUIRED(4001, "Resume file is required", HttpStatus.BAD_REQUEST),
        FILE_TOO_LARGE(4002, "File size must not exceed 5MB", HttpStatus.BAD_REQUEST),
        INVALID_FILE_TYPE(4003, "Only PDF files are allowed", HttpStatus.BAD_REQUEST),
        INVALID_FILE_CONTENT(4004, "Invalid PDF file content", HttpStatus.BAD_REQUEST),
        FILE_READ_FAILED(4005, "Failed to read resume file", HttpStatus.INTERNAL_SERVER_ERROR);





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
