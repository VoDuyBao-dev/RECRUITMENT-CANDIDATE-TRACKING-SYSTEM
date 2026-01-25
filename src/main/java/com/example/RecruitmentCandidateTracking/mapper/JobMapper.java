package com.example.RecruitmentCandidateTracking.mapper;

import com.example.RecruitmentCandidateTracking.dto.requests.JobPositionRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.JobPositionResponse;
import com.example.RecruitmentCandidateTracking.entities.JobPosition;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface JobMapper {
    
    /**
     * Convert JobPositionRequest to JobPosition entity
     */
    @Mapping(target = "id", ignore = true)
    // @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    JobPosition toEntity(JobPositionRequest request);
    
    /**
     * Convert JobPosition entity to JobPositionResponse
     */
    @Mapping(target = "createdByUsername", source = "entity.createdBy.fullName")
    @Mapping(target = "createdByUserId", source = "entity.createdBy.id")
    JobPositionResponse toResponse(JobPosition entity);
    
    /**
     * Convert list of JobPosition entities to list of JobPositionResponse
     */
    List<JobPositionResponse> toResponseList(List<JobPosition> entities);
    
    /**
     * Update existing JobPosition entity from JobPositionRequest
     * Only update non-null fields
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromRequest(JobPositionRequest request, @MappingTarget JobPosition entity);
}