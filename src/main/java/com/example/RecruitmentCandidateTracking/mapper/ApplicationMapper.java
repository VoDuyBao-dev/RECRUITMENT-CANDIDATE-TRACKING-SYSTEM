package com.example.RecruitmentCandidateTracking.mapper;

import com.example.RecruitmentCandidateTracking.dto.responses.ApplicationResponse;
import com.example.RecruitmentCandidateTracking.entities.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.*;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplicationMapper {

    @Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source = "job.id", target = "jobId")
    @Mapping(source = "job.title", target = "jobTitle")
    ApplicationResponse applicationToApplicationResponse(Application application);

    @Mapping(target = "candidateId", source = "candidate.id")
    @Mapping(target = "candidateName", source = "candidate.fullName")
    @Mapping(target = "candidateEmail", source = "candidate.email")
    @Mapping(target = "jobId", source = "job.id")
    @Mapping(target = "jobTitle", source = "job.title")
    @Mapping(target = "totalInterviews", expression = "java(entity.getInterviews() != null ? entity.getInterviews().size() : 0)")
    ApplicationResponse toApplicationResponse(Application entity);

    // @Mapping(target = "candidateId", source = "candidate.id")
    // @Mapping(target = "candidateName", source = "candidate.fullName")
    // @Mapping(target = "candidateEmail", source = "candidate.email")
    // @Mapping(target = "jobId", source = "job.id")
    // @Mapping(target = "jobTitle", source = "job.title")
    // @Mapping(target = "totalInterviews", expression = "java(entity.getInterviews() != null ? entity.getInterviews().size() : 0)")
    // ApplicationResponse toResponse(Application entities);

    // Trong ApplicationMapper
    // ApplicationResponse toApplicationResponse(Application application);
}
