package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.dto.PageResponse;
import com.example.RecruitmentCandidateTracking.dto.requests.JobPositionRequest;
import com.example.RecruitmentCandidateTracking.dto.responses.JobPositionResponse;
import com.example.RecruitmentCandidateTracking.entities.JobPosition;
import com.example.RecruitmentCandidateTracking.entities.User;
import com.example.RecruitmentCandidateTracking.enums.JobStatus;
import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.exceptions.ErrorCode;
import com.example.RecruitmentCandidateTracking.mapper.JobMapper;
import com.example.RecruitmentCandidateTracking.repositories.JobPositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobPositionService {

    private final JobPositionRepository jobPositionRepository;
    private final JobMapper jobMapper;
    private final AuthenticationService authenticationService;

    @Transactional
    public JobPositionResponse createJob(JobPositionRequest request) {

        // Validate dates
        validateJobDates(request.getStartDate(), request.getDeadline());

        // Get current logged-in user (HR)
        User currentUser = authenticationService.getCurrentUser();

        // Map request to entity
        JobPosition jobPosition = jobMapper.toEntity(request);

        // Set status (default to OPEN if not specified)
        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            jobPosition.setStatus(JobStatus.valueOf(request.getStatus().toUpperCase()));
        } else {
            jobPosition.setStatus(JobStatus.OPEN);
        }

        // Set created by user
        jobPosition.setCreatedBy(currentUser);

        // Set updatedAt
        jobPosition.setUpdatedAt(LocalDate.now());

        // Note: createdAt is automatically set by BaseEntity with @CreatedDate

        // Save to database
        JobPosition savedJob = jobPositionRepository.save(jobPosition);

        log.info("Job position created successfully with ID: {} by user: {}",
                savedJob.getId(), currentUser.getFullName());

        return jobMapper.toResponse(savedJob);
    }

    @Transactional
    public JobPositionResponse updateJob(Long id, JobPositionRequest request) {
        // log.info("Updating job position with ID: {}", id);

        // Find existing job
        JobPosition jobPosition = jobPositionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_FOUND));

        // Don't allow updating closed jobs
        if (jobPosition.getStatus() == JobStatus.CLOSED) {
            throw new AppException(ErrorCode.JOB_ALREADY_CLOSED);
        }

        // Validate new dates if provided
        LocalDate newStartDate = request.getStartDate() != null ? request.getStartDate() : jobPosition.getStartDate();
        LocalDate newDeadline = request.getDeadline() != null ? request.getDeadline() : jobPosition.getDeadline();
        validateJobDates(newStartDate, newDeadline);

        // Update fields from request (only non-null fields)
        jobMapper.updateEntityFromRequest(request, jobPosition);

        // Update updatedAt
        jobPosition.setUpdatedAt(LocalDate.now());

        // Save updated job
        JobPosition updatedJob = jobPositionRepository.save(jobPosition);

        // log.info("Job position updated successfully: {}", id);
        return jobMapper.toResponse(updatedJob);
    }

    @Transactional
    public JobPositionResponse changeStatusJob(Long id, String statusStr) { 

        JobPosition jobPosition = jobPositionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_FOUND));

        if (statusStr == null || statusStr.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_JOB_STATUS);
        }

        JobStatus newStatus;
        try {
            newStatus = JobStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_JOB_STATUS);
        }

        JobStatus currentStatus = jobPosition.getStatus();

        // if (currentStatus == null) {
        //     throw new AppException(ErrorCode.INVALID_JOB_STATUS); // Hoặc xử lý tùy logic
        // }

        // Không cho set lại cùng trạng thái
        if (currentStatus == newStatus) {
            throw new AppException(ErrorCode.JOB_STATUS_NOT_CHANGED);
        }

        jobPosition.setStatus(newStatus);
        jobPosition.setUpdatedAt(LocalDate.now());

        JobPosition savedJob = jobPositionRepository.save(jobPosition);

        return jobMapper.toResponse(savedJob);
    }


    @Transactional(readOnly = true)
    public PageResponse<JobPositionResponse> getAllInternalJobs(int page, int size) {
        // log.info("Fetching all internal job positions");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<JobPosition> jobs = jobPositionRepository.findAllInternal(pageable);

        List<JobPositionResponse> responseList = jobs.getContent().stream()
                .map(jobMapper::toResponse)
                .toList();

        // Trả về PageResponse
        return PageResponse.of(
                responseList,
                jobs.getNumber(),
                jobs.getSize(),
                jobs.getTotalElements(),
                jobs.getTotalPages());

    }

    @Transactional(readOnly = true)
    public JobPositionResponse getJobById(Long id) {
        log.info("Fetching job position with ID: {}", id);

        JobPosition jobPosition = jobPositionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_FOUND));

        return jobMapper.toResponse(jobPosition);
    }

    @Transactional(readOnly = true)
    public PageResponse<JobPositionResponse> searchJobsForCandidate(
            String keyword,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<JobPosition> jobs = jobPositionRepository.searchOpenJobsByKeyword(
                JobStatus.OPEN,
                (keyword == null || keyword.isBlank()) ? null : keyword.trim(),
                pageable
        );

        List<JobPositionResponse> responseList = jobs.getContent().stream()
                .map(jobMapper::toResponse)
                .toList();

        return PageResponse.of(
                responseList,
                jobs.getNumber(),
                jobs.getSize(),
                jobs.getTotalElements(),
                jobs.getTotalPages());
    }

    @Transactional(readOnly = true)
    public PageResponse<JobPositionResponse> getRelatedJobs(
            Long jobId, int page, int size) {

        JobPosition current = jobPositionRepository.findById(jobId)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_FOUND));

        Pageable pageable = PageRequest.of(
                0, size, Sort.by(Sort.Direction.DESC, "createdAt")
        );

        // Tìm job liên quan
        Page<JobPosition> relatedPage =
                jobPositionRepository.findRelatedJobs(
                        JobStatus.OPEN,
                        jobId,
                        current.getAddress(),
                        current.getBranchName(),
                        current.getTitle(),
                        pageable
                );

        List<JobPosition> result = new ArrayList<>(relatedPage.getContent());

        // Nếu chưa đủ -> lấy thêm các job mới nhất
        if (result.size() < size) {

            List<Long> excludedIds = result.stream()
                    .map(JobPosition::getId)
                    .toList();

            int remain = size - result.size();

            Pageable fallbackPageable = PageRequest.of(
                    0, remain, Sort.by(Sort.Direction.DESC, "createdAt")
            );

            Page<JobPosition> fallbackJobs =
                    jobPositionRepository.findFallbackJobs(
                            JobStatus.OPEN,
                            jobId,
                            excludedIds.isEmpty() ? List.of(-1L) : excludedIds,
                            fallbackPageable
                    );

            result.addAll(fallbackJobs.getContent());
        }

        // Map sang response
        List<JobPositionResponse> responseList = result.stream()
                .map(jobMapper::toResponse)
                .toList();

        return PageResponse.of(responseList, 0, size, responseList.size(), 1);
    }



    // @Transactional
    // public void deleteJob(Long id) {
    //     log.info("Deleting job position with ID: {}", id);

    //     // Check if job exists
    //     JobPosition jobPosition = jobPositionRepository.findById(id)
    //             .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_FOUND));

    //     // Delete job
    //     jobPositionRepository.deleteById(id);

    //     log.info("Job position deleted successfully: {}", id);
    // }

    /**
     * Validate job dates
     */
    private void validateJobDates(LocalDate startDate, LocalDate deadline) {
        LocalDate today = LocalDate.now();

        // Start date should not be in the past
        if (startDate.isBefore(today)) {
            throw new AppException(ErrorCode.INVALID_START_DATE);
        }

        // Deadline must be after start date
        if (deadline.isBefore(startDate)) {
            throw new AppException(ErrorCode.INVALID_DEADLINE);
        }
    }

}