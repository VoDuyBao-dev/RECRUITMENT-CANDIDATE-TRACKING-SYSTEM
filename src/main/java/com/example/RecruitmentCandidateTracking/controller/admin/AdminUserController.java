package com.example.RecruitmentCandidateTracking.controller.admin;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.PageResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.AdminUpdateUserRequest;
import com.example.RecruitmentCandidateTracking.dto.requests.CreateUserRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.AdminUserResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.UserResponse;
import com.example.RecruitmentCandidateTracking.services.AdminUserService;
import com.example.RecruitmentCandidateTracking.services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminUserController {

    AdminUserService adminUserService;
    UserService userService;

    @GetMapping
    public ApiResponse<PageResponse<AdminUserResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
            
        // return ApiResponse.success(adminUserService.getAllUsers());
        PageResponse<AdminUserResponse> pageResponse = adminUserService.getAllUsers(page, size);

        return ApiResponse.<PageResponse<AdminUserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Hiển thị danh sách người dùng thành công")
                .result(pageResponse)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AdminUserResponse> updateUserInfo(
            @PathVariable Long id,
            @RequestBody @Valid AdminUpdateUserRequest request) {

        // return ApiResponse.success(adminUserService.updateUserInfo(id, request));
        AdminUserResponse response = adminUserService.updateUserInfo(id, request);

        return ApiResponse.<AdminUserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Chỉnh sửa thông tin người dùng thành công")
                .result(response)
                .build();
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<AdminUserResponse> updateUserStatus(
            @PathVariable Long id) {

        // return ApiResponse.success(adminUserService.updateUserStatus(id));
        AdminUserResponse response = adminUserService.updateUserStatus(id);

        return ApiResponse.<AdminUserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Chỉnh sửa trạng thái tài khoản người dùng thành công")
                .result(response)
                .build();
    }

    @PostMapping("/add-user")
    public ApiResponse<UserResponse> addUser(@RequestBody @Valid CreateUserRequest createUserRequest) {

        UserResponse user = userService.createUserByAdmin(createUserRequest);

        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Thêm người dùng thành công")
                .result(user)
                .build();
    }


}
