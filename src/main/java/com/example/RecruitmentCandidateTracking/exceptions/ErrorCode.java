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
