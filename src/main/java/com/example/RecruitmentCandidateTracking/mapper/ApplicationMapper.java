package com.example.RecruitmentCandidateTracking.mapper;

import com.example.RecruitmentCandidateTracking.dto.responses.ApplicationResponse;
import com.example.RecruitmentCandidateTracking.entities.Application;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplicationMapper {
    
    @Mapping(target = "candidateId", source = "candidate.id")
    @Mapping(target = "candidateName", source = "candidate.fullName")
    @Mapping(target = "candidateEmail", source = "candidate.email")
    @Mapping(target = "jobId", source = "job.id")
    @Mapping(target = "jobTitle", source = "job.title")
    @Mapping(target = "totalInterviews", expression = "java(entity.getInterviews() != null ? entity.getInterviews().size() : 0)")
    ApplicationResponse toResponse(Application entity);
    

    List<ApplicationResponse> toResponseList(List<Application> entities);
}