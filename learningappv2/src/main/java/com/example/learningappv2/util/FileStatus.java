package com.example.learningappv2.util;

import lombok.Getter;

public enum FileStatus {

    IN_PROGRESS("IN_PROGRESS"),

    SUCCESS("SUCCESS"),

    ERROR("ERROR");

    @Getter
    private String status;

    FileStatus(String status) {
        this.status = status;
    }

}
