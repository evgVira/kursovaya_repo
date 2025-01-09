package com.example.learningappv2.controller;

import com.example.learningappv2.dto.SaveFileInfoResponseDto;
import com.example.learningappv2.service.FileEntitySaveService;
import com.example.learningappv2.service.FileUploadMinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadMinioService fileUploadMinioService;

    @PostMapping("/minio/upload")
    public void uploadFileToMinio(@RequestParam("file") MultipartFile file) {
        fileUploadMinioService.uploadFile(file);
    }

    @GetMapping("/minio/get")
    public ResponseEntity<InputStreamResource> getFileFromMinio(@RequestParam("file") String file, @RequestParam("bucket") String bucket){
        return fileUploadMinioService.getFileFromMinio(file, bucket);
    }

}
