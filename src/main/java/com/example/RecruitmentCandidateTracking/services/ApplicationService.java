package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.PageResponse;
import com.example.RecruitmentCandidateTracking.dto.responses.ApplicationResponse;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    AuthenticationService authenticationService;


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

    // thay đổi trạng thái trong quy trình tuyển dụng
    @Transactional
    public ApplicationResponse changePipelineStage(Long applicationId, PipelineStage newStage) {
        // log.info("Changing pipeline stage for application ID: {} to {}",
        // applicationId, newStage);

        authenticationService.getCurrentUser();

        // Find application
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));

        PipelineStage currentStage = application.getCurrentStage();

        // Validate stage transition
        validateStageTransition(currentStage, newStage);

        // Update stage
        application.setCurrentStage(newStage);

        // Save updated application
        Application updatedApplication = applicationRepository.save(application);

        // log.info("Pipeline stage changed successfully. Application ID: {}, Old stage:
        // {}, New stage: {}",
        // applicationId, currentStage, newStage);

        return applicationMapper.toApplicationResponse(updatedApplication);
    }

    // hr lấy tất cả đơn ứng tuyển cho một vị trí công việc
    @Transactional(readOnly = true)
    public PageResponse<ApplicationResponse> getApplicationsByJob(Long jobId, int page, int size) {

        // Verify job exists
        if (!jobPositionRepository.existsById(jobId)) {
            throw new AppException(ErrorCode.JOB_NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "appliedDate"));
        Page<Application> applicationPage = applicationRepository.findByJobId(jobId, pageable);

        List<ApplicationResponse> responseList = applicationPage.getContent().stream()
                .map(applicationMapper::toApplicationResponse)
                .toList();

        // Trả về PageResponse
        return PageResponse.of(
                responseList,
                applicationPage.getNumber(),
                applicationPage.getSize(),
                applicationPage.getTotalElements(),
                applicationPage.getTotalPages());
        // return applicationMapper.toApplicationResponse(applications);
    }

    // hr lấy tất cả đơn ứng tuyển đã nộp và có phân trang gồm 10 bản ghi mỗi trang
    @Transactional(readOnly = true)
    public PageResponse<ApplicationResponse> getAllApplications(int page, int size) {
        // 1. Tạo Pageable
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "appliedDate"));

        // 2. Gọi DB
        Page<Application> applicationPage = applicationRepository.findAll(pageable);

        // 3. Map Entity sang DTO
        List<ApplicationResponse> responseList = applicationPage.getContent().stream()
                .map(applicationMapper::toApplicationResponse)
                .toList();

        // 4. Trả về PageResponse
        return PageResponse.of(
                responseList,
                applicationPage.getNumber(),
                applicationPage.getSize(),
                applicationPage.getTotalElements(),
                applicationPage.getTotalPages());
    }

    // hr lấy chi tiết đơn ứng tuyển theo ID
    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(Long id) {
        // log.info("Fetching application with ID: {}", id);

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));

//        Chuyển từ resumeID sang link xem resume
        String linkResume = googleDriveService.getViewLink(application.getResumePath());
        application.setResumePath(linkResume);

        return applicationMapper.toApplicationResponse(application);
    }

    // hr lấy tất cả đơn ứng tuyển theo giai đoạn trong quy trình tuyển dụng
    @Transactional(readOnly = true)
    public PageResponse<ApplicationResponse> getApplicationsByStage(PipelineStage stage, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "appliedDate"));
        Page<Application> applicationPage = applicationRepository.findByCurrentStage(stage, pageable);

        // Map Entity sang DTO
        List<ApplicationResponse> responseList = applicationPage.getContent().stream()
                .map(applicationMapper::toApplicationResponse)
                .toList();

        // Trả về PageResponse
        return PageResponse.of(
                responseList,
                applicationPage.getNumber(),
                applicationPage.getSize(),
                applicationPage.getTotalElements(),
                applicationPage.getTotalPages());
    }

    // không thể chuyển từ rejected hoặc hired sang giai đoạn trước đó
    private void validateStageTransition(PipelineStage currentStage, PipelineStage newStage) {

        authenticationService.getCurrentUser();

        // 1. Không cho phép thay đổi nếu đã ở trạng thái kết thúc
        if (currentStage == PipelineStage.REJECTED || currentStage == PipelineStage.HIRED) {
            throw new AppException(ErrorCode.CANNOT_CHANGE_TERMINAL_STAGE);
        }

        // 2. Không cho phép chuyển sang chính nó
        if (currentStage == newStage) {
            throw new AppException(ErrorCode.INVALID_STAGE_TRANSITION);
        }

        // 3. Validate theo từng trạng thái hiện tại
        switch (currentStage) {

            case APPLIED:
                if (newStage != PipelineStage.SCREENING &&
                        newStage != PipelineStage.REJECTED) {
                    throw new AppException(ErrorCode.INVALID_STAGE_TRANSITION);
                }
                break;

            case SCREENING:
                if (newStage != PipelineStage.INTERVIEWING &&
                        newStage != PipelineStage.REJECTED) {
                    throw new AppException(ErrorCode.INVALID_STAGE_TRANSITION);
                }
                break;

            case INTERVIEWING:
                if (newStage != PipelineStage.OFFERED &&
                        newStage != PipelineStage.REJECTED) {
                    throw new AppException(ErrorCode.INVALID_STAGE_TRANSITION);
                }
                break;

            case OFFERED:
                if (newStage != PipelineStage.HIRED &&
                        newStage != PipelineStage.REJECTED) {
                    throw new AppException(ErrorCode.INVALID_STAGE_TRANSITION);
                }
                break;

            default:
                throw new AppException(ErrorCode.INVALID_STAGE_TRANSITION);
        }
    }



}