package com.example.RecruitmentCandidateTracking.mapper;

import com.example.RecruitmentCandidateTracking.dto.responses.EvaluationResponse;
import com.example.RecruitmentCandidateTracking.entities.Evaluation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EvaluationMapper {
    
    /**
     * Convert Evaluation entity to EvaluationResponse
     */
    @Mapping(target = "interviewId", source = "interview.id")
    @Mapping(target = "roundNumber", source = "interview.roundNumber")
    @Mapping(target = "roundName", source = "interview.roundName")
    @Mapping(target = "interviewerId", source = "interviewer.id")
    @Mapping(target = "interviewerName", source = "interviewer.fullName")
    EvaluationResponse toResponse(Evaluation entity);
    
    /**
     * Convert list of Evaluation entities to list of EvaluationResponse
     */
    List<EvaluationResponse> toResponseList(List<Evaluation> entities);
}