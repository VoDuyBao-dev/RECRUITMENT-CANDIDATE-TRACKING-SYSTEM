package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.responses.ApplicationResponse;
import com.example.RecruitmentCandidateTracking.entities.Application;
import com.example.RecruitmentCandidateTracking.enums.PipelineStage;
import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.exceptions.ErrorCode;
import com.example.RecruitmentCandidateTracking.mapper.ApplicationMapper;
import com.example.RecruitmentCandidateTracking.repositories.ApplicationRepository;
import com.example.RecruitmentCandidateTracking.repositories.JobPositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {
    
    private final ApplicationRepository applicationRepository;
    private final JobPositionRepository jobPositionRepository;
    // private final UserRepository userRepository;
    private final ApplicationMapper applicationMapper;
    private final AuthenticationService authenticationService;
    
// thay đổi trạng thái trong quy trình tuyển dụng 
    @Transactional
    public ApplicationResponse changePipelineStage(Long applicationId, PipelineStage newStage) {
        // log.info("Changing pipeline stage for application ID: {} to {}", applicationId, newStage);
        
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
        
        // log.info("Pipeline stage changed successfully. Application ID: {}, Old stage: {}, New stage: {}", 
        //          applicationId, currentStage, newStage);
        
        return applicationMapper.toResponse(updatedApplication);
    }

    
// hr lấy tất cả đơn ứng tuyển cho một vị trí công việc
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsByJob(Long jobId) {
        // log.info("Fetching all applications for job ID: {}", jobId);
        
        // Verify job exists
        if (!jobPositionRepository.existsById(jobId)) {
            throw new AppException(ErrorCode.JOB_NOT_FOUND);
        }
        
        List<Application> applications = applicationRepository.findByJobId(jobId);
        
        // log.info("Found {} applications for job ID: {}", applications.size(), jobId);
        
        return applicationMapper.toResponseList(applications);
    }
    
// hr lấy chi tiết đơn ứng tuyển theo ID
    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(Long id) {
        // log.info("Fetching application with ID: {}", id);
        
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));
        
        return applicationMapper.toResponse(application);
    }
    
// hr lấy tất cả đơn ứng tuyển theo giai đoạn trong quy trình tuyển dụng
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsByStage(PipelineStage stage) {
        // log.info("Fetching applications with stage: {}", stage);
        
        List<Application> applications = applicationRepository.findByCurrentStage(stage);
        
        // log.info("Found {} applications with stage: {}", applications.size(), stage);
        
        return applicationMapper.toResponseList(applications);
    }
    
// không thể chuyển từ rejected hoặc hired sang giai đoạn trước đó 
    private void validateStageTransition(PipelineStage currentStage, PipelineStage newStage) {
        authenticationService.getCurrentUser();

        // Cannot change from terminal stages
        if (currentStage == PipelineStage.REJECTED) {
            throw new AppException(ErrorCode.INVALID_STAGE_TRANSITION);
        }
        
        if (currentStage == PipelineStage.HIRED) {
            throw new AppException(ErrorCode.CANNOT_CHANGE_HIRED_STATUS);
        }
        
        // Cannot skip from APPLIED directly to OFFERED (must go through screening/interview)
        if (currentStage == PipelineStage.APPLIED && newStage == PipelineStage.OFFERED) {
            throw new AppException(ErrorCode.INVALID_STAGE_TRANSITION);
        }
        
        // Cannot go back from OFFERED to earlier stages
        if (currentStage == PipelineStage.OFFERED && 
            (newStage == PipelineStage.APPLIED || 
             newStage == PipelineStage.SCREENING || 
             newStage == PipelineStage.INTERVIEWING)) {
            throw new AppException(ErrorCode.INVALID_STAGE_TRANSITION);
        }
    }
    
}