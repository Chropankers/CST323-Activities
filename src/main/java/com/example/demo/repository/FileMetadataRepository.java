package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.FileMetadata;
import java.util.Optional;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    Optional<FileMetadata> findByUserIdAndFileName(Long userId, String fileName);
}