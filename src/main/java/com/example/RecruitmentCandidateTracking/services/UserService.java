package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.requests.CandidateRequest;
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

    public User createUserCandidate(CandidateRequest candidateRequest) {

        if (!candidateRequest.getPassword().equals(candidateRequest.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }
        if (userRepository.existsByEmail(candidateRequest.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(candidateRequest);

        user.setEmail(candidateRequest.getEmail());
        user.setStatus(UserStatus.PENDING);
        user.setEnabled(false);
        user.setPasswordHash(passwordEncoder.encode(candidateRequest.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.CANDIDATE.name());
        user.setRoles(roles);

        log.info("user in createUser{}", user);

        User savedUser=userRepository.save(user);

        return savedUser;
    }

//    public LearnerResponse createLearner(LearnerRequest learnerRequest) {
//        User user = createUser(learnerRequest);
//        Learner learner = Learner.builder()
//                .user(user)
//                .fullName(user.getFullName())
//                .build();
//        log.info("learner in createLearner{}", learner);
//
//        return learnerMapper.toResponse(learnerRepository.save(learner));
//
//    }
}
