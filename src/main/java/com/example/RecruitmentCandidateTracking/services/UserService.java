package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.requests.CandidateRequest;
import com.example.RecruitmentCandidateTracking.dto.requests.ChangePasswordRequest;
import com.example.RecruitmentCandidateTracking.dto.requests.UserUpdateInformationRequest;
import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.enums.Role;
import com.example.RecruitmentCandidateTracking.enums.UserStatus;
import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.exceptions.ErrorCode;
import com.example.RecruitmentCandidateTracking.mapper.UserMapper;
import com.example.RecruitmentCandidateTracking.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    UserMapper userMapper;
    VerificationTokenService verificationTokenService;

    public User createUserCandidate(CandidateRequest candidateRequest) {

        if (!candidateRequest.getPassword().equals(candidateRequest.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }
        if (userRepository.existsByEmail(candidateRequest.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(candidateRequest);
        log.info("User created: {}", user);

        user.setEmail(candidateRequest.getEmail());
        user.setStatus(UserStatus.PENDING);
        user.setEnabled(false);
        user.setPasswordHash(passwordEncoder.encode(candidateRequest.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.CANDIDATE.name());
        user.setRoles(roles);

        log.info("user in createUser{}", user);

        User savedUser=userRepository.save(user);

        verificationTokenService.generateAndSendVerifyLink(savedUser.getEmail());

        return savedUser;
    }

    //    Check user đã kích hoạt tài khoản chưa và các trạng thái khác...
    public void validateUserStatus(User user) {
        switch (user.getStatus()) {

            case PENDING ->
                    throw new AppException(ErrorCode.ACCOUNT_NOT_ACTIVATED);
            case INACTIVE ->
                    throw new AppException(ErrorCode.ACCOUNT_INACTIVE);
            case ACTIVE -> {}
            default ->
                    throw new AppException(ErrorCode.UNKNOWN_USER_STATUS);

        }

        if (Boolean.FALSE.equals(user.getEnabled())) {
            throw new AppException(ErrorCode.ACCOUNT_DISABLED);
        }
    }

    public void updateUser(UserUpdateInformationRequest user){
        String email = getEmailUser();
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        existingUser.setFullName(user.getFullName());

        userRepository.save(existingUser);

    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())){
            throw new AppException(ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }

        String email = getEmailUser();
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), existingUser.getPasswordHash())){
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        existingUser.setPasswordHash(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        userRepository.save(existingUser);

    }

//    Lấy email của user từ token
    public String getEmailUser(){
        String context = SecurityContextHolder.getContext().getAuthentication().getName();
        return context;

    }


}
