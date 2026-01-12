package com.shopit.shopit.global.file;

import com.shopit.shopit.domain.product.exception.option.ProductOptionImageFileRequiredException;
import com.shopit.shopit.domain.product.exception.option.ProductOptionImageStorageException;
import com.shopit.shopit.domain.product.exception.option.ProductOptionInvalidImageFormatException;
import com.shopit.shopit.global.config.FileProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileProperties fileProperties;

    public String saveImage(MultipartFile file, String subDir) {
        validateImage(file);

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = extractExtension(originalFilename);
            String storedFilename = UUID.randomUUID() + "." + extension;

            Path directoryPath = Paths.get(fileProperties.getRoot(), subDir);
            Files.createDirectories(directoryPath);

            Path filePath = directoryPath.resolve(storedFilename);
            file.transferTo(filePath);

            return storedFilename;

        } catch (Exception e) {
            log.error("이미지 저장 실패", e);
            throw new ProductOptionImageStorageException();
        }
    }

    public void deleteImage(String relativePath) {
        try {
            Path filePath = Paths.get(fileProperties.getRoot(), relativePath);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new RuntimeException("이미지 삭제 실패", e);
        }
    }

    /* ==================== validation ==================== */

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ProductOptionImageFileRequiredException();
        }

        String extension = extractExtension(file.getOriginalFilename()).toLowerCase();
        if (!isAllowedExtension(extension)) {
            throw new ProductOptionInvalidImageFormatException();
        }
    }

    private boolean isAllowedExtension(String extension) {
        return List.of(".jpg", ".jpeg", ".png", ".webp").contains(extension);
    }

    private String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("파일 확장자가 없습니다.");
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
