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
        USER_EXISTED(1002, "user existed", HttpStatus.BAD_REQUEST),
        USER_NOT_EXISTED(1005, "user not existed", HttpStatus.NOT_FOUND),
        SAVE_USER_FAILED(1014, "save user failed", HttpStatus.INTERNAL_SERVER_ERROR),
        UPDATE_USER_FAILED(1015, "update user failed", HttpStatus.INTERNAL_SERVER_ERROR),
        // lỗi invalid password
        // PASSWORD_INVALID(1003, "password must at least 8 charaters"),
        // USERNAME_INVALID(1004, "username must at least 2 charaters"),
        // Xử lí nhập vào sai key Enum
        INVALID_KEY(1001, "invalid message key", HttpStatus.BAD_REQUEST),
        UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
        UNAUTHORIZED(1017, "you do not have permission", HttpStatus.FORBIDDEN),

        INVALID_OTP(1008, "invalid otp", HttpStatus.BAD_REQUEST),
        EMAIL_SEND_FAILED(1009, "email send failed", HttpStatus.INTERNAL_SERVER_ERROR),
        OTP_SAVE_FAILED(1010, "otp save failed", HttpStatus.INTERNAL_SERVER_ERROR),
        OTP_EXPIRED(1011, "otp expired", HttpStatus.BAD_REQUEST),
        OTP_NOT_FOUND(1016, "otp not found for specified email", HttpStatus.NOT_FOUND),

        // loi login
        INVALID_CREDENTIALS(1012, "invalid credentials", HttpStatus.UNAUTHORIZED),
        PASSWORDS_DO_NOT_MATCH(1013, "Password and confirm password do not match", HttpStatus.BAD_REQUEST),

        // Admin manage users
        // CANNOT_DELETE_ADMIN(1018, "cannot delete admin user", HttpStatus.FORBIDDEN),
        USER_NOT_FOUND(1019, "user not found", HttpStatus.NOT_FOUND),
        INVALID_STATUS(1020, "invalid account status", HttpStatus.BAD_REQUEST),
        CANNOT_DELETE_ADMIN(1021, "cannot block admin user", HttpStatus.FORBIDDEN),
        EMAIL_ALREADY_EXISTS(1022, "email already exists", HttpStatus.BAD_REQUEST),
        USER_STATUS_UNCHANGED(1023, "user status is unchanged", HttpStatus.BAD_REQUEST),

        // TUTOR REGISTER VALIDATION
        // THÔNG TIN TÀI KHOẢN
        FULLNAME_REQUIRED(2001, "Full name is required", HttpStatus.BAD_REQUEST),
        FULLNAME_LENGTH_INVALID(2002, "Full name must be between 2 and 50 characters", HttpStatus.BAD_REQUEST),
        EMAIL_REQUIRED(2003, "Email is required", HttpStatus.BAD_REQUEST),
        EMAIL_INVALID(2004, "Invalid email format", HttpStatus.BAD_REQUEST),
        PASSWORD_REQUIRED(2005, "Password is required", HttpStatus.BAD_REQUEST),
        PASSWORD_TOO_SHORT(2006, "Password must be between 6 and 30 characters", HttpStatus.BAD_REQUEST),
        CONFIRM_PASSWORD_REQUIRED(2007, "Confirm password is required", HttpStatus.BAD_REQUEST),
        PHONE_REQUIRED(2008, "Phone number is required", HttpStatus.BAD_REQUEST),
        PHONE_INVALID(2009, "Invalid Vietnamese phone number format", HttpStatus.BAD_REQUEST),
        // THÔNG TIN CÁ NHÂN & NGHỀ NGHIỆP
        GENDER_REQUIRED(2010, "Gender is required", HttpStatus.BAD_REQUEST),
        ADDRESS_REQUIRED(2011, "Address is required", HttpStatus.BAD_REQUEST),
        ADDRESS_TOO_LONG(2012, "Address must not exceed 255 characters", HttpStatus.BAD_REQUEST),
        UNIVERSITY_REQUIRED(2013, "University name is required", HttpStatus.BAD_REQUEST),
        UNIVERSITY_TOO_LONG(2014, "University name must not exceed 100 characters", HttpStatus.BAD_REQUEST),
        CERTIFICATES_REQUIRED(2015, "At least one certificate is required", HttpStatus.BAD_REQUEST),
        CERTIFICATE_NAME_REQUIRED(2016, "Certificate name cannot be blank", HttpStatus.BAD_REQUEST),
        INTRODUCTION_REQUIRED(2017, "Introduction is required", HttpStatus.BAD_REQUEST),
        PRICE_REQUIRED(2019, "Price per hour is required", HttpStatus.BAD_REQUEST),
        PRICE_TOO_LOW(2020, "Minimum price per hour is 50,000 VND", HttpStatus.BAD_REQUEST),
        PRICE_TOO_HIGH(2021, "Maximum price per hour is 1,000,000 VND", HttpStatus.BAD_REQUEST),
        // THÔNG TIN DẠY HỌC
        SUBJECTS_REQUIRED(2022, "At least one subject must be selected", HttpStatus.BAD_REQUEST),
        SUBJECT_ID_REQUIRED(2023, "Subject ID required", HttpStatus.BAD_REQUEST),
        // UPLOAD FILE
        AVATAR_URL_REQUIRED(2024, "Avatar URL is required", HttpStatus.BAD_REQUEST),
        PROOF_URL_REQUIRED(2025, "Proof file URL is required", HttpStatus.BAD_REQUEST),
        EDUCATIONAL_LEVEL_REQUIRED(2026, "Educational level is required", HttpStatus.BAD_REQUEST),

        // RESPONSE VALIDATION (TUTOR REGISTER RESPONSE)
        TUTOR_ID_REQUIRED(2050, "Tutor ID is missing in response", HttpStatus.INTERNAL_SERVER_ERROR),
        VERIFICATION_STATUS_REQUIRED(2051, "Verification status is missing in response",
                        HttpStatus.INTERNAL_SERVER_ERROR),

        // TUTOR REGISTER EXCEPTION
        SUBJECT_NOT_SELECTED(2100, "No subjects selected", HttpStatus.BAD_REQUEST),
        PASSWORD_NOT_MATCH(2101, "Password and confirm password do not match", HttpStatus.BAD_REQUEST),
        FILE_UPLOAD_FAILED(2102, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR),

        FILE_SAVE_ERROR(2200, "Error saving file", HttpStatus.INTERNAL_SERVER_ERROR),
        URL_REQUIRE(2201, "File URL is required", HttpStatus.BAD_REQUEST),
        STACK_OVERFLOW(2202, "Internal server error",
                        HttpStatus.INTERNAL_SERVER_ERROR),
        SAVE_INVALIDATED_TOKEN_FAILED(2203, "save invalidated token failed", HttpStatus.INTERNAL_SERVER_ERROR),
        INVALID_FIELD(2204, "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),

        // TUTOR DASHBOARD
        TUTOR_NOT_FOUND(2300, "Tutor not found", HttpStatus.NOT_FOUND),
        INVALID_REQUEST(2303, "Invalid request", HttpStatus.BAD_REQUEST),
        INTERNAL_ERROR(2304, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
        METHOD_INVALID(2305, "Method is not supported", HttpStatus.BAD_REQUEST),

        // E_bool admin
        EBOOK_TITLE_REQUIRED(3001, "Title is required", HttpStatus.BAD_REQUEST),
        EBOOK_TITLE_TOO_LONG(3002, "Title must be at most 255 characters", HttpStatus.BAD_REQUEST),

        EBOOK_TYPE_REQUIRED(3003, "Ebook type is required", HttpStatus.BAD_REQUEST),

        EBOOK_FILE_REQUIRED(3004, "File is required", HttpStatus.BAD_REQUEST),
        // EBOOK_FILE_PATH_TOO_LONG(3005, "File path must be at most 255 characters",
        // HttpStatus.BAD_REQUEST),

        EBOOK_NOT_FOUND(3006, "Ebook not found", HttpStatus.NOT_FOUND),
        EBOOK_FORBIDDEN(3007, "You do not have permission to modify this ebook", HttpStatus.FORBIDDEN),

        // ========== VALIDATION – PROFILE ==========
        FULL_NAME_REQUIRED(2001, "Họ và tên không được để trống", HttpStatus.BAD_REQUEST),
        FULL_NAME_TOO_LONG(2002, "Họ và tên không được vượt quá 100 ký tự", HttpStatus.BAD_REQUEST),

        PHONE_NUMBER_REQUIRED(2003, "Số điện thoại không được để trống", HttpStatus.BAD_REQUEST),
        PHONE_NUMBER_INVALID(2004, "Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST),
        // ========== BUSINESS – PROFILE ==========
        USER_NOT_TUTOR(2102, "Tài khoản hiện tại không phải là gia sư", HttpStatus.FORBIDDEN),
        TUTOR_PROFILE_NOT_FOUND(2103, "Không tìm thấy hồ sơ gia sư", HttpStatus.NOT_FOUND),
        AVATAR_UPLOAD_FAILED(3402, "Không thể cập nhật ảnh đại diện", HttpStatus.INTERNAL_SERVER_ERROR),
        AVATAR_INVALID_TYPE(3403, "Chỉ chấp nhận file JPG, PNG, JPEG", HttpStatus.BAD_REQUEST),
        AVATAR_FILE_REQUIRED(3401, "File ảnh đại diện không được để trống", HttpStatus.BAD_REQUEST),
        PROOF_FILE_UPLOAD_FAILED(3503, "Không thể lưu file bằng cấp", HttpStatus.INTERNAL_SERVER_ERROR),
        PROOF_FILE_REQUIRED(3501, "File bằng cấp không được để trống", HttpStatus.BAD_REQUEST),
        INVALID_PROOF_FILE_TYPE(3502, "File chỉ được phép là PDF, PNG hoặc JPG", HttpStatus.BAD_REQUEST),

        // ========== ADMIN MANAGE TUTORS ==========
        TUTOR_USER_NOT_FOUND(1405, "Tutor does not have a valid user", HttpStatus.NOT_FOUND),
        USER_IS_NOT_TUTOR(1406, "User is not a tutor", HttpStatus.BAD_REQUEST),
        USER_STATUS_CANNOT_TOGGLE(1410, "User status cannot be toggled", HttpStatus.BAD_REQUEST),

        // ========== ADMIN MANAGE LEARNERS ==========
        LEARNER_USER_NOT_FOUND(1505, "Learner does not have a valid user", HttpStatus.NOT_FOUND),
        USER_IS_NOT_LEARNER(1506, "User is not a learner", HttpStatus.BAD_REQUEST),

        ACCESS_DENIED(1507, "Access denied", HttpStatus.FORBIDDEN),
        TUTOR_NOT_APPROVED(1508, "Tutor is not approved", HttpStatus.FORBIDDEN),

        // ==========Tutor CLASS REQUESTS ==========
        CLASS_REQUEST_NOT_FOUND(4009, "Class request not found", HttpStatus.NOT_FOUND),
        INVALID_REQUEST_STATUS(4010, "Invalid request status for this operation", HttpStatus.BAD_REQUEST),
        CLASS_NOT_FOUND(4011, "Class not found", HttpStatus.NOT_FOUND),

        // ACCOUNT
        ACCOUNT_NOT_ACTIVATED(4001, "account not activated", HttpStatus.BAD_REQUEST),
        ACCOUNT_DISABLED(4002, "account disabled", HttpStatus.BAD_REQUEST),
        ACCOUNT_LOCKED(4003, "account locked", HttpStatus.BAD_REQUEST),
        ACCOUNT_INACTIVE(4004, "account inactive", HttpStatus.BAD_REQUEST),
        UNKNOWN_USER_STATUS(4005, "unknown user status", HttpStatus.INTERNAL_SERVER_ERROR),
        INVALID_OTP_TYPE(4006, "Invalid OTP type", HttpStatus.BAD_REQUEST),
        OTP_TYPE_REQUIRED(4007, "OTP type required", HttpStatus.BAD_REQUEST),
        OTP_CODE_REQUIRED(4008, "OTP code required", HttpStatus.BAD_REQUEST),

        // tutor schedule
        DAY_OF_WEEK_REQUIRED(4300, "Day of week is required", HttpStatus.BAD_REQUEST),
        TIME_RANGE_REQUIRED(4301, " time range is required", HttpStatus.BAD_REQUEST),
        START_TIME_REQUIRED(4301, "Start time is required", HttpStatus.BAD_REQUEST),
        END_TIME_REQUIRED(4302, "End time is required", HttpStatus.BAD_REQUEST),
        AVAILABILITY_STATUS_REQUIRED(4303, "Availability status is required", HttpStatus.BAD_REQUEST),

        INVALID_TIME_RANGE(4304, "End time must be after start time", HttpStatus.BAD_REQUEST),
        TUTOR_AVAILABILITY_NOT_FOUND(4305, "Tutor availability not found", HttpStatus.NOT_FOUND),
        DUPLICATED_AVAILABILITY_SLOT(4307, "Availability with same day and time already exists",
                        HttpStatus.BAD_REQUEST),
        INVALID_AVAILABILITY_STATUS(4308, "Invalid availability status", HttpStatus.BAD_REQUEST),

        DATE_REQUIRED(4400, "Date is required", HttpStatus.BAD_REQUEST),
        AVAILABILITY_CONFLICT(4402, "This time slot already exists", HttpStatus.BAD_REQUEST),

        MISSING_PARAMETER(4009, "Missing required parameter", HttpStatus.BAD_REQUEST),

        // Rating and Review
        RATING_VALUE_INVALID(5001, "Rating value must be between 1 and 5", HttpStatus.BAD_REQUEST),
        CLASS_NOT_COMPLETED(5002, "Class not completed", HttpStatus.BAD_REQUEST),
        CLASS_ALREADY_RATED(5003, "Class already rated", HttpStatus.BAD_REQUEST),

        // Trial request
        TUTOR_ID_REQUIRE(5100, "Tutor ID is required", HttpStatus.BAD_REQUEST),
        TRIAL_DATE_REQUIRE(5101, "Trial date is required", HttpStatus.BAD_REQUEST),
        TRIAL_DATE_FUTURE(5102, "Trial date must be in the future", HttpStatus.BAD_REQUEST),
        ADDITIONAL_NOTES_TOO_LONG(5105, "Notes cannot exceed 500 characters", HttpStatus.BAD_REQUEST),
        SUBJECT_NOT_EXISTED(5106, "Subject not existed", HttpStatus.BAD_REQUEST),
        TUTOR_NOT_TEACH_SUBJECT(5107, "Tutor does not teach this subject", HttpStatus.BAD_REQUEST),
        END_TIME_BEFORE_START_TIME(5108, "End time must be after start time", HttpStatus.BAD_REQUEST),
        CLASS_DURATION_TOO_SHORT(5109, "Class duration must be at least 30 minutes", HttpStatus.BAD_REQUEST),
        CLASS_DURATION_EXCEEDS_MAX(5110, "Class duration cannot exceed 1 hour 30 minutes", HttpStatus.BAD_REQUEST),
        TUTOR_TIME_CONFLICT(5111, "Tutor is not available at this time slot", HttpStatus.BAD_REQUEST),
        DUPLICATE_TRIAL_REQUEST(5112, "You already have a pending or confirmed trial request with this tutor",
                        HttpStatus.BAD_REQUEST),
        LEARNER_TIME_CONFLICT(5113, "Learner time conflict", HttpStatus.BAD_REQUEST),
        SCHEDULE_NOT_FOUND(5114, "Schedule not found", HttpStatus.BAD_REQUEST),
        SCHEDULE_CONFLICT(5115, "Trùng lịch với người học khác", HttpStatus.BAD_REQUEST),
        // official request
        SESSIONS_PER_WEEK_MAX_3(5115, "Sessions per week max 3", HttpStatus.BAD_REQUEST),
        SESSIONS_PER_WEEK_MIN_1(5116, "Sessions per week min 1", HttpStatus.BAD_REQUEST),
        SESSIONS_PER_WEEK_REQUIRED(5117, "Sessions per week required", HttpStatus.BAD_REQUEST),
        SCHEDULE_SIZE_INVALID(5118, "Schedule size invalid", HttpStatus.BAD_REQUEST),

        END_DATE_BEFORE_START_DATE(5119, "The end date cannot be earlier than the start date", HttpStatus.BAD_REQUEST),
        NO_SESSION_GENERATED(5120,
                        "No valid study sessions could be generated from the selected date range and weekly schedules",
                        HttpStatus.BAD_REQUEST),
        TIME_CONFLICT(5121,
                        "The selected schedule conflicts with an existing class. Please choose a different day or time",
                        HttpStatus.BAD_REQUEST),
        START_DATE_REQUIRED(5122, "Start date required", HttpStatus.BAD_REQUEST),
        END_DATE_REQUIRED(5123, "End date required", HttpStatus.BAD_REQUEST),
        START_DATE_MUST_BE_FUTURE(5124, "Start date must be future", HttpStatus.BAD_REQUEST),
        SCHEDULE_REQUIRED(5125, "Schedule required", HttpStatus.BAD_REQUEST),

        // tutor change password
        NEW_PASSWORD_SAME_AS_OLD(6002, "New password must be different from the current password",
                        HttpStatus.BAD_REQUEST),
        PASSWORD_CONFIRM_MISMATCH(6003, "New password and confirm password do not match", HttpStatus.BAD_REQUEST),
        INVALID_PASSWORD(6004, "Current password is incorrect", HttpStatus.BAD_REQUEST),
        LEARNER_NOT_FOUND(7001, "Learner not found", HttpStatus.NOT_FOUND),
        CERTIFICATE_NOT_FOUND(8001, "Certificate not found", HttpStatus.NOT_FOUND),
        STICKER_NOT_FOUND(9001, "Sticker not found", HttpStatus.NOT_FOUND),
        INVALID_CHAT_MESSAGE(9002, "Invalid chat message", HttpStatus.BAD_REQUEST),

        // tutor lịch rảnh
        AVAILABILITY_NOT_FOUND(9003, "không có lịch rảnh ở khung giờ này", HttpStatus.NOT_FOUND),
//    Payment
PAYMENT_NOT_FOUND(5300, "Payment not found", HttpStatus.BAD_REQUEST),
    PAYMENT_ALREADY_EXISTS(5301, "Lớp học này đã có giao dịch thanh toán", HttpStatus.BAD_REQUEST),
    REFUND_ALREADY_EXISTS(5302, "Lớp học này đã được hoàn tiền", HttpStatus.BAD_REQUEST),
    FAILED_TO_CALL_VNPAY(5303, "Gửi yêu cầu hoàn tiền đến VNPAY thất bại", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_AMOUNT(5304, "Số tiền khongo hợp lệ", HttpStatus.BAD_REQUEST),
    REFUND_FAILED(5305, "Hoàn tiền thất bại", HttpStatus.BAD_REQUEST),

//    revenue
    INVALID_DATE_RANGE(5400, "Invalid date range", HttpStatus.BAD_REQUEST),

Reason_required(5401, "Reason required", HttpStatus.BAD_REQUEST),
        
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
