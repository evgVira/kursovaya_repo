package com.example.learningappv2.service;

import com.example.learningappv2.dto.SaveFileInfo;
import com.example.learningappv2.mapper.FileEntityMapper;
import com.example.learningappv2.repository.FileEntityRepository;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import static com.example.learningappv2.util.FileStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FileUploadMinioServiceImpl implements FileUploadMinioService {

    private final FileEntityMapper fileEntityMapper;

    private final MinioClient minioClient;

    private final FileEntitySaveService fileEntitySaveService;

    private final FileEntityRepository fileEntityRepository;

    @Value("${minio.bucket}")
    private String minioBucketName;

    @Override
    public void uploadFile(MultipartFile file) {
        SaveFileInfo saveFileWithInProgressStatus = fileEntityMapper.mapToSaveFileInfo(file, IN_PROGRESS.getStatus());
        fileEntitySaveService.saveInfoByUploadedFile(saveFileWithInProgressStatus);
        checkCurrentFileStatus(saveFileWithInProgressStatus.getFileName());
            try {
                uploadToMinio(file);
            } catch (Exception e) {
                SaveFileInfo saveWithErrorStatus = fileEntityMapper.mapToSaveFileInfo(file, ERROR.getStatus());
                fileEntitySaveService.saveInfoByUploadedFile(saveWithErrorStatus);
                checkCurrentFileStatus(saveWithErrorStatus.getFileName());
                throw new RuntimeException(e.getMessage());
            }
            SaveFileInfo saveFileWithSuccessStatus = fileEntityMapper.mapToSaveFileInfo(file, SUCCESS.getStatus());
            fileEntitySaveService.saveInfoByUploadedFile(saveFileWithSuccessStatus);
            checkCurrentFileStatus(saveFileWithSuccessStatus.getFileName());
    }

    @Override
    public ResponseEntity<InputStreamResource> getFileFromMinio(String file, String bucket) {
        try {
            InputStreamResource inputStreamResource = getFromMinio(file, bucket);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(inputStreamResource);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void uploadToMinio(MultipartFile file) throws Exception {
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(minioBucketName)
                .object(file.getOriginalFilename())
                .stream(file.getInputStream(), file.getSize(), -1)
                .build();
        minioClient.putObject(putObjectArgs);
    }

    private InputStreamResource getFromMinio(String filename, String bucketName) throws Exception {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .build();
        InputStream inputStream = minioClient.getObject(getObjectArgs);
        return new InputStreamResource(inputStream);
    }

    private void checkCurrentFileStatus(String fileName) {
        var file = fileEntityRepository.findFileEntityByFileName(fileName);
        if (file.isPresent()) {
            var fileEntity = file.get();
            log.info(String.format("file with fileName :: %s and fileId :: %s has %s status", fileEntity.getFileName(), fileEntity.getFileId(), fileEntity.getFileStatus()));
        }
    }
}
