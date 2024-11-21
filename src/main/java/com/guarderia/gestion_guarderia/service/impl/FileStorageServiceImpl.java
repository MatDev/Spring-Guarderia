package com.guarderia.gestion_guarderia.service.impl;

import com.guarderia.gestion_guarderia.exception.FileStorageExeption;
import com.guarderia.gestion_guarderia.service.FileStorageService;

import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service


public class FileStorageServiceImpl implements FileStorageService {
    private final Path rootLocation= Paths.get("upload");
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FileStorageServiceImpl.class);

    public FileStorageServiceImpl() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new FileStorageExeption("No se pudo crear el directorio donde se almacenarán los archivos",e);
        }
    }

    @Override
    public String storeFile(MultipartFile file, String subfolder) throws IOException {
        LOGGER.info("Almacenando archivo: {}" , file.getOriginalFilename());

        Path folderLocation = rootLocation.resolve(subfolder);
        Files.createDirectories(folderLocation);


        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path destinationFile = folderLocation.resolve(filename).normalize();

        if (!destinationFile.toAbsolutePath().startsWith(folderLocation.toAbsolutePath())) {
            LOGGER.error("Intento de almacenamiento fuera del directorio d esignado filename {} con path {}", filename, destinationFile.getParent());
            throw new SecurityException("Intento de almacenamiento fuera del directorio designado.");
        }

        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        return destinationFile.toString();

    }

    @Override
    public Resource loadFileAsResource(String path) throws MalformedURLException {
        LOGGER.info("Cargando archivo: {} " , path);
        try {
            Path filePath = Paths.get(path.replace("\\", "/")).normalize();
            Resource resource = new org.springframework.core.io.FileSystemResource(filePath.toFile());
            if(resource.exists()||resource.isReadable()){
                LOGGER.info("Archivo cargado: {}" , path);
                return resource;

            }else{
                LOGGER.error("No se pudo leer el archivo:{} con path normalizado {}", path, filePath);
                throw new FileStorageExeption("No se pudo leer el archivo:" + path);
            }
        }catch (Exception e){
            LOGGER.error("No se pudo leer el archivo:{}", path);
            throw new FileStorageExeption("No se pudo leer el archivo:"+ path,e);
        }
    }

    @Override
    public void deleteFile(String path) throws IOException {
        LOGGER.info("Eliminando archivo: {}" , path);
        Path filePath = rootLocation.resolve(path).normalize();
        File file=filePath.toFile();
        if(file.exists()){
            file.delete();
            LOGGER.info("Archivo eliminado: {}" , path);
        }else{
            LOGGER.error("No se pudo encontrar el archivo:{}", path);
            throw new FileStorageExeption("No se pudo encontrar el archivo: " +path);
        }

    }
}
