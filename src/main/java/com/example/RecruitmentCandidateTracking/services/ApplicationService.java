package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.repsonses.ApplicationResponse;
import com.example.RecruitmentCandidateTracking.entities.Application;
import com.example.RecruitmentCandidateTracking.entities.JobPosition;
import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.enums.PipelineStage;
import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.exceptions.ErrorCode;
import com.example.RecruitmentCandidateTracking.mapper.ApplicationMapper;
import com.example.RecruitmentCandidateTracking.repositories.ApplicationRepository;
import com.example.RecruitmentCandidateTracking.repositories.JobPositionRepository;
import com.example.RecruitmentCandidateTracking.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationService {

    ApplicationRepository applicationRepository;
    JobPositionRepository jobPositionRepository;
    UserRepository userRepository;
   GoogleDriveService googleDriveService;
   UserService userService;
    ApplicationMapper applicationMapper;


    public ApplicationResponse submitApplication(Long jobId, MultipartFile resumeFile) {

        String userEmail = userService.getEmailUser();
        validateResumeFile(resumeFile);

        User candidate = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new AppException(ErrorCode.CANDIDATE_NOT_EXISTED));

        JobPosition job = jobPositionRepository.findById(jobId)
            .orElseThrow(() -> new AppException(ErrorCode.JOB_POSITION_NOT_EXISTED));

        if (applicationRepository.existsByCandidateAndJob(candidate, job)) {
            throw new AppException(ErrorCode.ALREADY_APPLIED_JOB);
        }

        String resumePath = googleDriveService.uploadResume(
            resumeFile,
            candidate.getFullName(),
            job.getTitle()
        );

        Application application = Application.builder()
            .candidate(candidate)
            .job(job)
            .resumePath(resumePath)
            .currentStage(PipelineStage.APPLIED)
            .build();

        Application saved = applicationRepository.save(application);
        log.info("Application submitted: User {} -> Job {}", userEmail, job.getTitle());

        return applicationMapper.applicationToApplicationResponse(saved);
    }

    public List<ApplicationResponse> getCandidateApplications(String userEmail) {
        User candidate = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new AppException(ErrorCode.CANDIDATE_NOT_EXISTED));

        return applicationRepository.findByCandidate(candidate)
            .stream()
            .map(applicationMapper::applicationToApplicationResponse)
            .toList();
    }

    public String getResumeViewLink(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_EXISTED));

        return googleDriveService.getViewLink(application.getResumePath());
    }

    private void validateResumeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorCode.FILE_REQUIRED);
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new AppException(ErrorCode.FILE_TOO_LARGE);
        }

        //Check extension
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        }

        //Check MIME type
        if (!"application/pdf".equalsIgnoreCase(file.getContentType())) {
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        }

        //Check PDF magic number
        try {
            byte[] bytes = file.getBytes();
            String header = new String(bytes, 0, Math.min(bytes.length, 4));
            if (!header.startsWith("%PDF")) {
                throw new AppException(ErrorCode.INVALID_FILE_CONTENT);
            }

        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_READ_FAILED);
        }
    }



}