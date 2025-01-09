package com.example.learningappv2.controller;

import com.example.learningappv2.service.FileUploadMinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadMinioService fileUploadMinioService;

    @PostMapping(value = "/minio/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFileToMinio(@RequestPart(value = "file") MultipartFile file) {
        fileUploadMinioService.uploadFile(file);
    }

    @GetMapping("/minio/get")
    public ResponseEntity<InputStreamResource> getFileFromMinio(@RequestParam("file") String file, @RequestParam("bucket") String bucket) {
        return fileUploadMinioService.getFileFromMinio(file, bucket);
    }

}
