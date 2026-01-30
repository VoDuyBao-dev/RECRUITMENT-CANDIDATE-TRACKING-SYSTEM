package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.requests.CreateOfferRequest;
import com.example.RecruitmentCandidateTracking.dto.requests.RespondOfferRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.OfferResponse;
import com.example.RecruitmentCandidateTracking.entities.Application;
import com.example.RecruitmentCandidateTracking.entities.Offer;
import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.enums.PipelineStage;
import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.exceptions.ErrorCode;
import com.example.RecruitmentCandidateTracking.mapper.OfferMapper;
import com.example.RecruitmentCandidateTracking.repositories.ApplicationRepository;
import com.example.RecruitmentCandidateTracking.repositories.OfferRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OfferService {
    OfferRepository offerRepository;
    ApplicationRepository applicationRepository;
    UserService userService;
    EmailService emailService;
    OfferMapper offerMapper;

    public OfferResponse createOffer(CreateOfferRequest request) {

        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));

        if (offerRepository.existsByApplication(application)) {
            throw new AppException(ErrorCode.OFFER_ALREADY_EXISTS);
        }

        if (!application.getCurrentStage().name().equals("INTERVIEWING")) {
            throw new AppException(ErrorCode.INVALID_APPLICATION_STAGE);
        }

        // Người duyệt
        User approvedBy = userService.getCurrentUser();

        Offer offer = Offer.builder()
                .application(application)
                .approvedBy(approvedBy)
                .startWorkDate(request.getStartWorkDate())
                .basicSalary(request.getBasicSalary())
                .probationSalary(request.getProbationSalary())
                .contractType(request.getContractType())
                .build();

        Offer savedOffer = offerRepository.save(offer);

        // 5. Gửi email thông báo cho candidate
        emailService.sendOfferEmail(
                application.getCandidate().getEmail(),
                application.getCandidate().getFullName(),
                application.getJob().getTitle(),
                offer.getContractType().name(),
                offer.getStartWorkDate().toString(),
                offer.getBasicSalary().toString(),
                offer.getProbationSalary() != null
                        ? offer.getProbationSalary().toString()
                        : "Theo thỏa thuận",
                approvedBy.getFullName());

        // Chuyển trạng thái đơn ứng tuyển sang OFFERED
        application.setCurrentStage(PipelineStage.OFFERED);
        applicationRepository.save(application);

        return offerMapper.toOfferResponse(savedOffer);
    }

    // candidate đồng ý/ từ chối offer
    public void respondToOffer(RespondOfferRequest request) {

        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_EXISTED));

        // check application đã có offer chưa
        boolean checkOffer = offerRepository.existsByApplication(application);

        if (!checkOffer) {
            throw new AppException(ErrorCode.APPLICATION_HAS_NO_OFFER);
        }

        if (application.getCurrentStage().name().equals("HIRED")) {
            throw new AppException(ErrorCode.OFFER_ALREADY_RESPONDED);
        }

        User currentUser = userService.getCurrentUser();

        // Kiểm tra đúng candidate sở hữu application
        if (!application.getCandidate().getId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.CANDIDATE_NOT_OWNER_OF_APPLICATION);
        }

        if (Boolean.TRUE.equals(request.getAccepted())) {
            application.setCurrentStage(PipelineStage.HIRED);
        } else {
            application.setCurrentStage(PipelineStage.REJECTED);
        }

        applicationRepository.save(application);
    }
}
