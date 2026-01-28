package com.example.RecruitmentCandidateTracking.mapper;

import com.example.RecruitmentCandidateTracking.dto.repsonses.ApplicationResponse;
import com.example.RecruitmentCandidateTracking.entities.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source = "job.id", target = "jobId")
    @Mapping(source = "job.title", target = "jobTitle")
    ApplicationResponse applicationToApplicationResponse(Application application);
}
