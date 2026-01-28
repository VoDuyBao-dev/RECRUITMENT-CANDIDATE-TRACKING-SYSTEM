package com.example.RecruitmentCandidateTracking.controller.candidate;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.RespondOfferRequest;
import com.example.RecruitmentCandidateTracking.services.OfferService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate/offer")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CandidateOfferController {
    OfferService offerService;

    @PostMapping("/respond")
    public ApiResponse<Void> respondToOffer(@RequestBody @Valid RespondOfferRequest request
    ) {

        offerService.respondToOffer(request);

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Phản hồi offer thành công")
                .build();
    }
}
