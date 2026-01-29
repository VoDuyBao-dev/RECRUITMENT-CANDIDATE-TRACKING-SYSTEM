package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.responses.CandidateAppliedJobResponse;
import com.example.RecruitmentCandidateTracking.entities.Application;
import com.example.RecruitmentCandidateTracking.entities.Interview;
import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.enums.PipelineStage;
import com.example.RecruitmentCandidateTracking.mapper.ApplicationMapper;
import com.example.RecruitmentCandidateTracking.mapper.InterviewMapper;
import com.example.RecruitmentCandidateTracking.repositories.ApplicationRepository;
import com.example.RecruitmentCandidateTracking.repositories.InterviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CandidateApplicationService {

     ApplicationRepository applicationRepository;
     InterviewRepository interviewRepository;
     ApplicationMapper applicationMapper;
    InterviewMapper interviewMapper;
    UserService userService;

    public List<CandidateAppliedJobResponse> getAppliedJobs() {
        User currentUser = userService.getCurrentUser();
        Long candidateId = currentUser.getId();

        List<Application> applications =
                applicationRepository.findByCandidateIdOrderByAppliedDateDesc(candidateId);

        return applications.stream()
                .map(application -> {

                    CandidateAppliedJobResponse response =
                            applicationMapper.toAppliedJobResponse(application);

                    // chỉ khi đang INTERVIEWING
                    if (application.getCurrentStage() == PipelineStage.INTERVIEWING) {

                        List<Interview> interviews = interviewRepository.findUpcomingInterview(application.getId());

                        if (!interviews.isEmpty()) {
                            response = CandidateAppliedJobResponse.builder()
                                    .applicationId(response.getApplicationId())
                                    .currentStage(response.getCurrentStage())
                                    .appliedDate(response.getAppliedDate())
                                    .jobId(response.getJobId())
                                    .jobTitle(response.getJobTitle())
                                    .address(response.getAddress())
                                    .salary(response.getSalary())
                                    .interviewSchedule(
                                            interviewMapper.toScheduleResponse(interviews.get(0))
                                    )
                                    .build();
                        }
                    }

                    return response;
                })
                .toList();
    }
}
