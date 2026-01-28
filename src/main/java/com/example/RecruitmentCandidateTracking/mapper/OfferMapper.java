package com.example.RecruitmentCandidateTracking.mapper;

import com.example.RecruitmentCandidateTracking.dto.responses.OfferResponse;
import com.example.RecruitmentCandidateTracking.entities.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OfferMapper {
    @Mapping(target = "approvedByName", source = "approvedBy.fullName")
    @Mapping(target = "applicationId", source = "application.id")
    OfferResponse toOfferResponse(Offer offer);
}
