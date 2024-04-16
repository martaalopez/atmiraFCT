package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import java.nio.file.Files;

import org.springframework.web.multipart.MultipartFile;
@CrossOrigin(origins = "${Front_URL}")
@RestController
public class FileController {
    @Autowired
    private final StorageService storageService;
    @Autowired
    private final HttpServletRequest request;
    @Autowired
    public FileController(StorageService storageService, HttpServletRequest request) {
        this.storageService = storageService;
        this.request = request;
    }


    /**
     * Endpoint para subir un archivo.
     *
     * @param multipartFile Archivo a subir.
     * @return Mapa que contiene la URL del archivo subido.
     */

    @CrossOrigin(origins = "${Front_URL}")
    @PostMapping("/media/upload")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String path = storageService.store(multipartFile);
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/media/")
                .path(path)
                .toUriString();
        return Map.of("url", url);
    }

    /**
     * Obtiene un archivo por su nombre de archivo.
     *
     * @param filename Nombre del archivo.
     * @return El archivo como un recurso.
     * @throws IOException Si ocurre un error al cargar el archivo.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/media/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        Resource file = (Resource) storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }
}
