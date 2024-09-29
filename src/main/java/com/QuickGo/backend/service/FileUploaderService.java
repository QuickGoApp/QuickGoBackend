package com.QuickGo.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploaderService {
    String uploadFile(MultipartFile file);
    ResponseEntity<byte[]> getFile(String fileName);
}
