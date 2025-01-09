package com.example.learningappv2.service;

import com.example.learningappv2.dto.SaveFileInfo;
import com.example.learningappv2.dto.SaveFileInfoResponseDto;
import com.example.learningappv2.mapper.FileEntityMapper;
import com.example.learningappv2.model.FileEntity;
import com.example.learningappv2.repository.FileEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.learningappv2.util.FileStatus.IN_PROGRESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileEntitySaveServiceImpl implements FileEntitySaveService {

    private final FileEntityRepository fileEntityRepository;

    private final FileEntityMapper fileEntityMapper;

    @Override
    @Transactional
    public SaveFileInfoResponseDto saveInfoByUploadedFile(SaveFileInfo saveFileInfo) {
        Optional<FileEntity> file = fileEntityRepository.findFileEntityByFileName(saveFileInfo.getFileName());
        if (file.isPresent()) {
            FileEntity currentFile = file.get();
            if (saveFileInfo.getFileStatus().equals(IN_PROGRESS.getStatus()) && currentFile.getFileStatus().equals(IN_PROGRESS.getStatus())) {
                log.info("this file is in IN_PROGRESS status, cannot save or update now!");
                return null;
            }
            FileEntity updatedFile = fileEntityMapper.updateFileEntity(file.get(), saveFileInfo);
            fileEntityRepository.save(updatedFile);
            log.info("file was updated");
            return fileEntityMapper.mapToSaveFileInfoResponse(updatedFile);
        }
        FileEntity newFile = fileEntityMapper.mapToFileEntity(saveFileInfo);
        fileEntityRepository.save(newFile);
        log.info("file was saved");
        return fileEntityMapper.mapToSaveFileInfoResponse(newFile);
    }


}
