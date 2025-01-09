package com.example.learningappv2.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadMinioService{

    void uploadFile(MultipartFile file);

    ResponseEntity<InputStreamResource> getFileFromMinio(String file, String bucket);

}
