package com.example.RecruitmentCandidateTracking.controller.hr;

import com.example.RecruitmentCandidateTracking.dto.ApiResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.CreateOfferRequest;
import com.example.RecruitmentCandidateTracking.dto.requests.JobPositionRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.JobPositionResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.OfferResponse;
import com.example.RecruitmentCandidateTracking.services.OfferService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr/offers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class HROfferController {
    OfferService offerService;

    @PostMapping
    public ApiResponse<OfferResponse> createAndSendOffer(@Valid @RequestBody CreateOfferRequest request) {

        OfferResponse offer = offerService.createOffer(request);

        return ApiResponse.<OfferResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo và gửi thư mời làm việc thành công")
                .result(offer)
                .build();
    }

}
