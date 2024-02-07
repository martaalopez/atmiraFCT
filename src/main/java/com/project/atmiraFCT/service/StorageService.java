package com.project.atmiraFCT.service;

import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    void init() throws IOException;

    String store(MultipartFile file);

    Resource loadAsResource(String filename);
}
