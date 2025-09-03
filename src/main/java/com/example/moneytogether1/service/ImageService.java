package com.example.moneytogether1.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {

    private final String uploadDir;

    public ImageService(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadDir = uploadDir.endsWith("/") ? uploadDir : uploadDir + "/";
    }

    // 1) 멀티파트 파일 저장
    public String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + fileName; // URL로 반환
    }

    // 2) 외부 URL 그대로 저장 (구글 프로필 같은 경우)
    public String saveImageFromUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("이미지 URL이 비어있습니다.");
        }
        return imageUrl; // 그냥 DB에 저장
    }

    // 3) 기존 이미지 삭제
    public void deleteImage(String imageName) {
        if (imageName != null && !imageName.startsWith("http")) { // 외부 URL은 삭제 안 함
            try {
                Path path = Paths.get(uploadDir + imageName);
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

