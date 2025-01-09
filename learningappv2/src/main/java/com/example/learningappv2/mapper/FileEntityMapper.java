package com.example.learningappv2.mapper;

import com.example.learningappv2.dto.SaveFileInfo;
import com.example.learningappv2.dto.SaveFileInfoResponseDto;
import com.example.learningappv2.model.FileEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileEntityMapper {


    public FileEntity mapToFileEntity(SaveFileInfo saveFileInfo) {
        return FileEntity.builder()
                .fileName(saveFileInfo.getFileName())
                .fileStatus(saveFileInfo.getFileStatus())
                .build();
    }

    public FileEntity updateFileEntity(FileEntity file, SaveFileInfo saveFileInfo) {
        file.setFileName(saveFileInfo.getFileName());
        file.setFileStatus(saveFileInfo.getFileStatus());
        return file;
    }

    public SaveFileInfoResponseDto mapToSaveFileInfoResponse(FileEntity file) {
        return SaveFileInfoResponseDto.builder()
                .fileName(file.getFileName())
                .fileStatus(file.getFileStatus())
                .build();
    }

    public SaveFileInfo mapToSaveFileInfo(MultipartFile file, String status) {
        return SaveFileInfo.builder()
                .fileName(file.getOriginalFilename())
                .fileStatus(status)
                .build();
    }

}
