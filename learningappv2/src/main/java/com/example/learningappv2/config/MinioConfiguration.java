package com.example.learningappv2.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Value("${minio.login}")
    private String minioLogin;

    @Value("${minio.password}")
    private String minioPassword;

    @Value("${minio.url}")
    private String minioUrl;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .credentials(minioLogin, minioPassword)
                .endpoint(minioUrl)
                .build();
    }
}
