package com.example.RecruitmentCandidateTracking.services;

import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import com.example.RecruitmentCandidateTracking.exceptions.ErrorCode;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.AccessLevel;

import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GoogleDriveService {

    Drive drive;
    String folderCVs;

    public GoogleDriveService(
            Drive drive,
            @Value("${google.drive.folder-CVs}") String folderCVs
    ) {
        this.drive = drive;
        this.folderCVs = folderCVs;
    }


    public String uploadResume(MultipartFile file, String candidateName, String jobTitle) {
        try {
            String fileName = generateFileName(candidateName, jobTitle, file.getOriginalFilename());

            File metadata = new File();
            metadata.setName(fileName);

            // Folder trong My Drive của USER
            if (folderCVs != null && !folderCVs.isBlank()) {
                metadata.setParents(Collections.singletonList(folderCVs));
            }

            var mediaContent = new com.google.api.client.http.InputStreamContent(
                    file.getContentType(),
                    file.getInputStream()
            );

            File uploadedFile = drive.files()
                    .create(metadata, mediaContent)
                    .setFields("id, name, webViewLink")
                    .execute();

            log.info("Uploaded CV: {} | FileId={}", fileName, uploadedFile.getId());
            return uploadedFile.getId();

        } catch (IOException e) {
            log.error("Upload resume failed", e);
            throw new AppException(ErrorCode.DRIVE_UPLOAD_FAILED);
        }
    }


    public String getViewLink(String fileId) {
        try {
            File file = drive.files()
                    .get(fileId)
                    .setFields("webViewLink")
                    .execute();

            return file.getWebViewLink();

        } catch (IOException e) {
            throw new AppException(ErrorCode.DRIVE_GET_LINK_FAILED);
        }
    }


//    public void deleteResume(String fileId) {
//        try {
//            drive.files()
//                    .delete(fileId)
//                    .execute();
//
//            log.info("Deleted CV: {}", fileId);
//
//        } catch (IOException e) {
//            log.error("Delete resume failed", e);
//            throw new AppException(ErrorCode.DRIVE_DELETE_FAILED);
//        }
//    }

    public void testDriveConnection() {
        try {
            var about = drive.about()
                    .get()
                    .setFields("user(emailAddress)")
                    .execute();

            log.info("Drive connected as user: {}", about.getUser().getEmailAddress());

        } catch (IOException e) {
            throw new AppException(ErrorCode.DRIVE_CONNECTION_FAILED);
        }
    }

    private String generateFileName(
            String candidateName,
            String jobTitle,
            String originalFileName
    ) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        return String.format(
                "CV_%s_%s_%s%s",
                candidateName.replaceAll("\\s+", "_"),
                jobTitle.replaceAll("\\s+", "_"),
                timestamp,
                extension
        );
    }
}
