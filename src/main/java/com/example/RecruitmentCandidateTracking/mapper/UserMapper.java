package com.example.RecruitmentCandidateTracking.mapper;

import com.example.RecruitmentCandidateTracking.dto.requests.CandidateRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.AdminUserResponse;
import com.example.RecruitmentCandidateTracking.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(CandidateRequest candidateRequest);
    
    AdminUserResponse toAdminResponse(User user);
}
