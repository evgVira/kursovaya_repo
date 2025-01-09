package com.example.learningappv2.service;

import com.example.learningappv2.dto.SaveFileInfo;
import com.example.learningappv2.dto.SaveFileInfoResponseDto;

public interface FileEntitySaveService {

    SaveFileInfoResponseDto saveInfoByUploadedFile(SaveFileInfo saveFileInfo);

}
