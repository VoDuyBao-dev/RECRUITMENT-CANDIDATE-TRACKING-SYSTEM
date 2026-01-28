package com.example.RecruitmentCandidateTracking.mapper;

import com.example.RecruitmentCandidateTracking.dto.requests.JobPositionRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.JobPositionResponse;
import com.example.RecruitmentCandidateTracking.entities.JobPosition;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface JobMapper {

    // chuyển một yêu cầu JobPositionRequest thành một thực thể JobPosition
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "branchName", source = "request.branchName")
    JobPosition toEntity(JobPositionRequest request);

    // chuyển một thực thể JobPosition thành một phản hồi JobPositionResponse
    @Mapping(target = "createdByUsername", source = "entity.createdBy.fullName")
    @Mapping(target = "createdByUserId", source = "entity.createdBy.id")
    JobPositionResponse toResponse(JobPosition entity);

    // chuyển một danh sách các thực thể JobPosition thành một danh sách các phản
    // hồi JobPositionResponse
    List<JobPositionResponse> toResponseList(List<JobPosition> entities);

    // cập nhật một thực thể JobPosition từ một yêu cầu JobPositionRequest, bỏ qua
    // các trường không được phép cập nhật
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromRequest(JobPositionRequest request, @MappingTarget JobPosition entity);
}