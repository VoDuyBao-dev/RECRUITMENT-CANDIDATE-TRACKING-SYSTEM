package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.PageResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.EvaluationRequest;
import com.example.RecruitmentCandidateTracking.dto.requests.InterviewRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.ApplicationResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.EvaluationResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.InterviewResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.InterviewersResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.JobPositionResponse;
import com.example.RecruitmentCandidateTracking.entities.*;
import com.example.RecruitmentCandidateTracking.enums.ERole;
import com.example.RecruitmentCandidateTracking.enums.InterviewType;
import com.example.RecruitmentCandidateTracking.enums.PipelineStage;
import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.exceptions.ErrorCode;
import com.example.RecruitmentCandidateTracking.mapper.EvaluationMapper;
import com.example.RecruitmentCandidateTracking.mapper.InterviewMapper;
import com.example.RecruitmentCandidateTracking.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final EvaluationRepository evaluationRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final InterviewMapper interviewMapper;
    private final EvaluationMapper evaluationMapper;
    private final AuthenticationService authenticationService;

    // hr lên lịch phỏng vấn
    @Transactional
    public InterviewResponse scheduleInterview(InterviewRequest request) {
        // log.info("Scheduling interview for application ID: {}",
        // request.getApplicationId());

        // Validate end time is after start time
        if (request.getEndTime().isBefore(request.getScheduledTime())) {
            throw new AppException(ErrorCode.INVALID_INTERVIEW_TIME);
        }

        // Find application
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));

        // Validate application is in appropriate stage
        if (application.getCurrentStage() != PipelineStage.SCREENING && application.getCurrentStage() != PipelineStage.INTERVIEWING) {
            throw new AppException(ErrorCode.CANNOT_SCHEDULE_INTERVIEW);
        }
// kiểm tra roundNumber có hợp lệ hay trùng không 
        List<Interview> existingInterviews = interviewRepository.findByApplicationIdAndRoundNumber(
                request.getApplicationId(),
                request.getRoundNumber());
        if (!existingInterviews.isEmpty()) {
            throw new AppException(ErrorCode.INTERVIEW_ROUND_NUMBER_EXISTS);
        }   

        // Find interviewers
        // Find interviewers
        Set<User> interviewers = new HashSet<>();

        for (Long interviewerId : request.getInterviewerIds()) {

            User interviewer = userRepository.findById(interviewerId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            // 1. Check role của interviewer
            // if (!interviewer.hasRole(ERole.INTERVIEWER) &&
            // !interviewer.hasRole(ERole.HR) &&
            // !interviewer.hasRole(ERole.ADMIN)) {

            // throw new AppException(ErrorCode.INVALID_INTERVIEWER_ROLE);
            // }
            // 1. Check role của interviewer
            if (interviewer.hasRole(ERole.CANDIDATE)) {
                throw new AppException(ErrorCode.INVALID_INTERVIEWER_ROLE);
            }

            // 2. Check interviewer active (nếu có field status)
            if (!interviewer.isActive()) {
                throw new AppException(ErrorCode.INTERVIEWER_INACTIVE);
            }

            // 3. Check trùng lịch phỏng vấn
            List<Interview> conflicts = interviewRepository.findConflictingInterviews(
                    interviewerId,
                    request.getScheduledTime(),
                    request.getEndTime());

            if (!conflicts.isEmpty()) {
                throw new AppException(ErrorCode.INTERVIEWER_TIME_CONFLICT);
            }

            interviewers.add(interviewer);
        }

        // Create interview
        Interview interview = Interview.builder()
                .application(application)
                .interviewers(interviewers)
                .roundNumber(request.getRoundNumber())
                .roundName(request.getRoundName())
                .scheduledTime(request.getScheduledTime())
                .endTime(request.getEndTime())
                .interviewType(InterviewType.valueOf(request.getInterviewType().toUpperCase()))
                .build();

        // Save interview
        Interview savedInterview = interviewRepository.save(interview);

        // Update application stage to INTERVIEWING
        application.setCurrentStage(PipelineStage.INTERVIEWING);
        applicationRepository.save(application);

        return interviewMapper.toResponse(savedInterview);
    }

    /**
     * Update interview schedule
     */
    @Transactional
    public InterviewResponse updateInterview(Long interviewId, InterviewRequest request) {
        // log.info("Updating interview ID: {}", interviewId);

        // Find interview
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new AppException(ErrorCode.INTERVIEW_NOT_FOUND));

        // Validate end time is after start time
        if (request.getEndTime().isBefore(request.getScheduledTime())) {
            throw new AppException(ErrorCode.INVALID_INTERVIEW_TIME);
        }

        // Check if interview has already been evaluated
        List<Evaluation> evaluations = evaluationRepository.findListByInterviewId(interviewId);
        if (!evaluations.isEmpty()) {
            throw new AppException(ErrorCode.CANNOT_UPDATE_EVALUATED_INTERVIEW);
        }

        // Update interviewers if provided
        if (request.getInterviewerIds() != null && !request.getInterviewerIds().isEmpty()) {
            Set<User> newInterviewers = new HashSet<>();
            for (Long interviewerId : request.getInterviewerIds()) {
                User interviewer = userRepository.findById(interviewerId)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

                // Check for time conflicts (excluding current interview)
                List<Interview> conflicts = interviewRepository.findConflictingInterviews(
                        interviewerId,
                        request.getScheduledTime(),
                        request.getEndTime());
                conflicts.removeIf(i -> i.getId().equals(interviewId));

                if (!conflicts.isEmpty()) {
                    throw new AppException(ErrorCode.INTERVIEWER_TIME_CONFLICT);
                }

                newInterviewers.add(interviewer);
            }
            interview.setInterviewers(newInterviewers);
        }

        // Update other fields
        interview.setScheduledTime(request.getScheduledTime());
        interview.setEndTime(request.getEndTime());
        if (request.getRoundName() != null) {
            interview.setRoundName(request.getRoundName());
        }
        if (request.getInterviewType() != null) {
            interview.setInterviewType(InterviewType.valueOf(request.getInterviewType().toUpperCase()));
        }

        // Save updated interview
        Interview updatedInterview = interviewRepository.save(interview);

        // log.info("Interview updated successfully. ID: {}", interviewId);

        return interviewMapper.toResponse(updatedInterview);
    }

    @Transactional
    public EvaluationResponse submitEvaluation(Long interviewId, EvaluationRequest request) {
        // log.info("Submitting evaluation for interview ID: {}", interviewId);

        User currentUser = authenticationService.getCurrentUser();

        // Find interview
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new AppException(ErrorCode.INTERVIEW_NOT_FOUND));

        // Validate that current user is one of the assigned interviewers
        boolean isAssignedInterviewer = interview.getInterviewers().stream()
                .anyMatch(interviewer -> interviewer.getId().equals(currentUser.getId()));

        if (!isAssignedInterviewer) {
            throw new AppException(ErrorCode.NOT_ASSIGNED_INTERVIEWER);
        }

        // Check if this interviewer already submitted evaluation
        if (evaluationRepository.existsByInterviewIdAndInterviewerId(interviewId, currentUser.getId())) {
            throw new AppException(ErrorCode.EVALUATION_ALREADY_SUBMITTED);
        }

        // Create evaluation
        Evaluation evaluation = Evaluation.builder()
                .interview(interview)
                .interviewer(currentUser)
                .score(request.getScore())
                .comment(request.getComment())
                .updatedAt(LocalDate.now())
                .build();

        // Save evaluation
        Evaluation savedEvaluation = evaluationRepository.save(evaluation);

        // Check if all interviewers have submitted evaluations
        List<Evaluation> allEvaluations = evaluationRepository.findListByInterviewId(interviewId);
        if (allEvaluations.size() == interview.getInterviewers().size()) {
            // All interviewers have evaluated
            // Calculate average score and update application stage
            Double averageScore = evaluationRepository.getAverageScoreByInterviewId(interviewId);

            Application application = interview.getApplication();

            if (averageScore != null && averageScore < 50) {
                application.setCurrentStage(PipelineStage.REJECTED);

            }

            applicationRepository.save(application);
        }

        return evaluationMapper.toResponse(savedEvaluation);
    }

    @Transactional(readOnly = true)
    public List<InterviewResponse> getMySchedule() {
        User currentUser = authenticationService.getCurrentUser();

        List<Interview> interviews = interviewRepository.findUpcomingInterviewsByInterviewer(
                currentUser.getId(),
                LocalDateTime.now());

        return interviewMapper.toResponseList(interviews);
    }

    @Transactional(readOnly = true)
    public PageResponse<InterviewResponse> getInterviewsByApplication(Long applicationId, int page, int size) {
        // log.info("Fetching interviews for application ID: {}", applicationId);
        Pageable pageable = PageRequest.of(page, size);
        // Verify application exists
        if (!applicationRepository.existsById(applicationId)) {
            throw new AppException(ErrorCode.APPLICATION_NOT_FOUND);
        }

        Page<Interview> interviews = interviewRepository.findByApplicationId(applicationId, pageable);

        List<InterviewResponse> responseList = interviews.getContent().stream()
                .map(interviewMapper::toResponse)
                .toList();

        return PageResponse.of(
                responseList,
                interviews.getNumber(),
                interviews.getSize(),
                interviews.getTotalElements(),
                interviews.getTotalPages());
        // return interviewMapper.toResponse(interviews);
    }

    /**
     * Get evaluations for an interview
     */
    @Transactional(readOnly = true)
    public PageResponse<EvaluationResponse> getEvaluationsByInterview(Long interviewId, int page, int size) {
        // log.info("Fetching evaluations for interview ID: {}", interviewId);

        // Verify interview exists
        if (!interviewRepository.existsById(interviewId)) {
            throw new AppException(ErrorCode.INTERVIEW_NOT_FOUND);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Evaluation> evaluations = evaluationRepository.findByInterviewId(interviewId, pageable);

        List<EvaluationResponse> responseList = evaluations.getContent().stream()
                .map(evaluationMapper::toResponse)
                .toList();

        // Trả về PageResponse
        return PageResponse.of(
                responseList,
                evaluations.getNumber(),
                evaluations.getSize(),
                evaluations.getTotalElements(),
                evaluations.getTotalPages());
    }

    @Transactional(readOnly = true)
    public PageResponse<EvaluationResponse> getCompletedEvaluationsByInterview(
            Long interviewId, int page, int size) {

        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new AppException(ErrorCode.INTERVIEW_NOT_FOUND));

        int totalInterviewers = interview.getInterviewers().size();

        long totalEvaluations = evaluationRepository.countEvaluationsByInterviewId(interviewId);

        // Nếu chưa đủ interviewer đánh giá → trả rỗng
        if (totalEvaluations < totalInterviewers) {
            return PageResponse.of(
                    List.of(),
                    page,
                    size,
                    0,
                    0);
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Evaluation> evaluations = evaluationRepository.findByInterviewId(interviewId, pageable);

        List<EvaluationResponse> responseList = evaluations.getContent().stream()
                .map(evaluationMapper::toResponse)
                .toList();

        return PageResponse.of(
                responseList,
                evaluations.getNumber(),
                evaluations.getSize(),
                evaluations.getTotalElements(),
                evaluations.getTotalPages());
    }

    @Transactional(readOnly = true)
    public List<InterviewersResponse> getAllInterviewers() {

        return userRepository.findAllInterviewers(ERole.INTERVIEWER.name())
                .stream()
                .map(interviewMapper::toInterviewersResponse)
                .toList();
    }

}