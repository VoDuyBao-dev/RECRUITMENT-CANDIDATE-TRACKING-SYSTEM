package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.responses.InterviewEvaluationDetailResponse;
import com.example.RecruitmentCandidateTracking.entities.Evaluation;
import com.example.RecruitmentCandidateTracking.entities.Interview;
import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.exceptions.ErrorCode;
import com.example.RecruitmentCandidateTracking.mapper.EvaluationMapper;
import com.example.RecruitmentCandidateTracking.mapper.InterviewMapper;
import com.example.RecruitmentCandidateTracking.repositories.EvaluationRepository;
import com.example.RecruitmentCandidateTracking.repositories.InterviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InterviewEvaluationService {

     InterviewRepository interviewRepository;
     EvaluationRepository evaluationRepository;

     InterviewMapper interviewMapper;
     EvaluationMapper evaluationItemMapper;

    public InterviewEvaluationDetailResponse getInterviewEvaluationDetail(Long interviewId) {

        Interview interview = interviewRepository.findDetailById(interviewId)
                .orElseThrow(() -> new AppException(ErrorCode.INTERVIEW_NOT_FOUND));

        List<Evaluation> evaluations = evaluationRepository.findByInterviewId(interviewId);

        InterviewEvaluationDetailResponse response = interviewMapper.toDetailResponse(interview);

        response.setEvaluations(evaluationItemMapper.toItemResponseList(evaluations));

        return response;
    }
}

