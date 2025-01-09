package com.example.learningappv2.repository;

import com.example.learningappv2.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findFileEntityByFileName(String fileName);
}
