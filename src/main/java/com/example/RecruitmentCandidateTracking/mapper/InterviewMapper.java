package com.example.RecruitmentCandidateTracking.mapper;

import com.example.RecruitmentCandidateTracking.dto.responses.InterviewEvaluationDetailResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.InterviewResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.InterviewScheduleResponse;
import com.example.RecruitmentCandidateTracking.entities.Interview;
import com.example.RecruitmentCandidateTracking.entities.User;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InterviewMapper {
    
// trả về một InterviewResponse từ một Interview entity
    @Mapping(target = "applicationId", source = "application.id")
    @Mapping(target = "candidateName", source = "application.candidate.fullName")
    @Mapping(target = "jobTitle", source = "application.job.title")
    @Mapping(target = "interviewers", expression = "java(mapInterviewers(entity.getInterviewers()))")
    @Mapping(target = "hasEvaluation", expression = "java(false)") // Will be set in service
    InterviewResponse toResponse(Interview entity);
    
// trả về một danh sách InterviewResponse từ một danh sách Interview entity
    List<InterviewResponse> toResponseList(List<Interview> entities);
    
// ánh xạ tập hợp các User (người phỏng vấn) thành tập hợp các InterviewerInfo trong InterviewResponse
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

    @Mapping(target = "interviewId", source = "id")
    @Mapping(target = "applicationId", source = "application.id")
    @Mapping(target = "candidateId", source = "application.candidate.id")
    @Mapping(target = "candidateName", source = "application.candidate.fullName")
    @Mapping(target = "jobTitle", source = "application.job.title")
    @Mapping(target = "evaluations", ignore = true) // set trong service
    InterviewEvaluationDetailResponse toDetailResponse(Interview interview);

    @Mapping(target = "interviewId", source = "id")
    @Mapping(target = "applicationId", source = "application.id")
    InterviewScheduleResponse toScheduleResponse(Interview interview);
}