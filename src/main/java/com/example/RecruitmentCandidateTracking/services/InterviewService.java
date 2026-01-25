package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.requests.EvaluationRequest;
import com.example.RecruitmentCandidateTracking.dto.requests.InterviewRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.EvaluationResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.InterviewResponse;
import com.example.RecruitmentCandidateTracking.entities.*;
import com.example.RecruitmentCandidateTracking.enums.InterviewType;
import com.example.RecruitmentCandidateTracking.enums.PipelineStage;
import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.exceptions.ErrorCode;
import com.example.RecruitmentCandidateTracking.mapper.EvaluationMapper;
import com.example.RecruitmentCandidateTracking.mapper.InterviewMapper;
import com.example.RecruitmentCandidateTracking.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    
    /**
     * HR schedules an interview
     */
    @Transactional
    public InterviewResponse scheduleInterview(InterviewRequest request) {
        log.info("Scheduling interview for application ID: {}", request.getApplicationId());
        
        // Validate end time is after start time
        if (request.getEndTime().isBefore(request.getScheduledTime())) {
            throw new AppException(ErrorCode.INVALID_INTERVIEW_TIME);
        }
        
        // Find application
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));
        
        // Validate application is in appropriate stage
        if (application.getCurrentStage() == PipelineStage.REJECTED || 
            // application.getCurrentStage() == PipelineStage.WITHDRAWN ||
            application.getCurrentStage() == PipelineStage.HIRED) {
            throw new AppException(ErrorCode.CANNOT_SCHEDULE_INTERVIEW);
        }
        
        // Find interviewers
        Set<User> interviewers = new HashSet<>();
        for (Long interviewerId : request.getInterviewerIds()) {
            User interviewer = userRepository.findById(interviewerId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            
            // Check for time conflicts
            List<Interview> conflicts = interviewRepository.findConflictingInterviews(
                interviewerId, 
                request.getScheduledTime(), 
                request.getEndTime()
            );
            
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
        
        log.info("Interview scheduled successfully. ID: {}, Round: {}", 
                 savedInterview.getId(), savedInterview.getRoundNumber());
        
        return interviewMapper.toResponse(savedInterview);
    }
    
    /**
     * Update interview schedule
     */
    @Transactional
    public InterviewResponse updateInterview(Long interviewId, InterviewRequest request) {
        log.info("Updating interview ID: {}", interviewId);
        
        // Find interview
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new AppException(ErrorCode.INTERVIEW_NOT_FOUND));
        
        // Validate end time is after start time
        if (request.getEndTime().isBefore(request.getScheduledTime())) {
            throw new AppException(ErrorCode.INVALID_INTERVIEW_TIME);
        }
        
        // Check if interview has already been evaluated
        List<Evaluation> evaluations = evaluationRepository.findByInterviewId(interviewId);
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
                    request.getEndTime()
                );
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
        
        log.info("Interview updated successfully. ID: {}", interviewId);
        
        return interviewMapper.toResponse(updatedInterview);
    }
    
    /**
     * Interviewer submits evaluation
     */
    @Transactional
    public EvaluationResponse submitEvaluation(Long interviewId, EvaluationRequest request) {
        log.info("Submitting evaluation for interview ID: {}", interviewId);
        
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
        List<Evaluation> allEvaluations = evaluationRepository.findByInterviewId(interviewId);
        if (allEvaluations.size() == interview.getInterviewers().size()) {
            // All interviewers have evaluated
            // Calculate average score and update application stage
            Double averageScore = evaluationRepository.getAverageScoreByInterviewId(interviewId);
            
            Application application = interview.getApplication();
            
            // Suggest stage change based on average score
            if (averageScore != null && averageScore >= 7.0) {
                // High score - move to OFFERED
                application.setCurrentStage(PipelineStage.OFFERED);
                log.info("Application {} passed interview with average score {}", 
                         application.getId(), averageScore);
            } else if (averageScore != null && averageScore < 5.0) {
                // Low score - reject
                application.setCurrentStage(PipelineStage.REJECTED);
                log.info("Application {} failed interview with average score {}", 
                         application.getId(), averageScore);
            }
            // If score is between 5-7, keep in INTERVIEWING for further rounds
            
            applicationRepository.save(application);
        }
        
        
        return evaluationMapper.toResponse(savedEvaluation);
    }
    
    /**
     * Get interviewer's schedule
     */
    @Transactional(readOnly = true)
    public List<InterviewResponse> getMySchedule() {
        User currentUser = authenticationService.getCurrentUser();
        
        // log.info("Fetching interview schedule for interviewer: {}", currentUser.getUsername());
        
        List<Interview> interviews = interviewRepository.findUpcomingInterviewsByInterviewer(
            currentUser.getId(), 
            LocalDateTime.now()
        );
        
        log.info("Found {} upcoming interviews for interviewer ID: {}", 
                 interviews.size(), currentUser.getId());
        
        return interviewMapper.toResponseList(interviews);
    }
    
    /**
     * Get all interviews for an application
     */
    @Transactional(readOnly = true)
    public List<InterviewResponse> getInterviewsByApplication(Long applicationId) {
        log.info("Fetching interviews for application ID: {}", applicationId);
        
        // Verify application exists
        if (!applicationRepository.existsById(applicationId)) {
            throw new AppException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        
        List<Interview> interviews = interviewRepository.findByApplicationId(applicationId);
        
        log.info("Found {} interviews for application ID: {}", interviews.size(), applicationId);
        
        return interviewMapper.toResponseList(interviews);
    }
    
    /**
     * Get evaluations for an interview
     */
    @Transactional(readOnly = true)
    public List<EvaluationResponse> getEvaluationsByInterview(Long interviewId) {
        log.info("Fetching evaluations for interview ID: {}", interviewId);
        
        // Verify interview exists
        if (!interviewRepository.existsById(interviewId)) {
            throw new AppException(ErrorCode.INTERVIEW_NOT_FOUND);
        }
        
        List<Evaluation> evaluations = evaluationRepository.findByInterviewId(interviewId);
        
        log.info("Found {} evaluations for interview ID: {}", evaluations.size(), interviewId);
        
        return evaluationMapper.toResponseList(evaluations);
    }
    
}