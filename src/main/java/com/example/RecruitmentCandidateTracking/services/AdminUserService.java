package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.PageResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.AdminUpdateUserRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.AdminUserResponse;
import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.enums.UserStatus;
import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.exceptions.ErrorCode;
import com.example.RecruitmentCandidateTracking.mapper.UserMapper;
import com.example.RecruitmentCandidateTracking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    public PageResponse<AdminUserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<User> userPage = userRepository.findAll(pageable);

        List<AdminUserResponse> responseList = userPage.getContent().stream()
                .map(userMapper::toAdminResponse)
                .toList();
        // Trả về PageResponse
        return PageResponse.of(
                responseList,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages());
    }

    public AdminUserResponse updateUserInfo(Long userId, AdminUpdateUserRequest request) {

        User user = authenticationService.getCurrentUser();
        if (!user.getId().equals(userId)) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        }
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRoles(request.getRoles());

        User updatedUser = userRepository.save(user);

        return userMapper.toAdminResponse(updatedUser);
    }

    public AdminUserResponse updateUserStatus(Long userId) {

        User user = authenticationService.getCurrentUser();
        if (!user.getId().equals(userId)) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        }
        if (user.getStatus().equals(UserStatus.ACTIVE)) {
            user.setStatus(UserStatus.INACTIVE);
            user.setEnabled(false);
        } else {
            user.setStatus(UserStatus.ACTIVE);
            user.setEnabled(true);
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toAdminResponse(updatedUser);
    }
}