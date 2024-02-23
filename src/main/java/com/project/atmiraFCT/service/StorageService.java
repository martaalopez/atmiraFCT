package com.project.atmiraFCT.service;

import org.springframework.core.io.Resource; // Importar Resource de Spring

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init() throws IOException;

    String store(MultipartFile file);

    Resource loadAsResource(String filename);
}

