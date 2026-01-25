package com.example.RecruitmentCandidateTracking.mapper;

import com.example.RecruitmentCandidateTracking.dto.responses.InterviewResponse;
import com.example.RecruitmentCandidateTracking.entities.Interview;
import com.example.RecruitmentCandidateTracking.entities.User;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InterviewMapper {
    
    /**
     * Convert Interview entity to InterviewResponse
     */
    @Mapping(target = "applicationId", source = "application.id")
    @Mapping(target = "candidateName", source = "application.candidate.fullName")
    @Mapping(target = "jobTitle", source = "application.job.title")
    @Mapping(target = "interviewers", expression = "java(mapInterviewers(entity.getInterviewers()))")
    @Mapping(target = "hasEvaluation", expression = "java(false)") // Will be set in service
    InterviewResponse toResponse(Interview entity);
    
    /**
     * Convert list of Interview entities to list of InterviewResponse
     */
    List<InterviewResponse> toResponseList(List<Interview> entities);
    
    /**
     * Map interviewers to InterviewerInfo
     */
    default Set<InterviewResponse.InterviewerInfo> mapInterviewers(Set<User> interviewers) {
        if (interviewers == null) {
            return null;
        }
        return interviewers.stream()
                .map(user -> InterviewResponse.InterviewerInfo.builder()
                        .id(user.getId())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toSet());
    }
}