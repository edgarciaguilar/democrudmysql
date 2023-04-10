package com.crud.democrudmysql.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.crud.democrudmysql.exception.SFileNotFoundException;
import com.crud.democrudmysql.exception.StorageException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileSystemStorageService implements StorageService{
    
    @Value("${storage.public.path}")
    private String storagePublicPath;

    @PostConstruct
    @Override
    public void init() {
        log.info("El metodo init esta siendo ejecutado");
        try{
            Files.createDirectories(Paths.get(storagePublicPath));
        } catch(IOException e) {
            throw new StorageException("No se pudo inicializar ubicacion de almacenamiento");
        }
    }

    @Override
    public String store(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID() + "." +
                StringUtils.getFilenameExtension(originalFilename);

        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file " + filename);
        }

        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, Paths.get(storagePublicPath).resolve(filename));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
        return filename;
    }

    @Override
    public Path load(String filename) {
        return Paths.get(storagePublicPath).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new SFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new SFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void delete(String filename) {
        Path file = load(filename);
        try {
            FileSystemUtils.deleteRecursively(file);
        } catch (IOException e) {
        }
    }


}
