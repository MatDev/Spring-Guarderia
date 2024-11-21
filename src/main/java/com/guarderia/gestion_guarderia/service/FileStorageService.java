package com.guarderia.gestion_guarderia.service;

import com.guarderia.gestion_guarderia.exception.FileStorageExeption;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/*
@Service
public class FileStorageService {
    private final Path rootLocation= Paths.get("upload");

    public FileStorageService() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new FileStorageExeption("No se pudo crear el directorio donde se almacenarán los archivos",e);
        }
    }

    public String storeFile(MultipartFile multipartFile, String stringsubfolder) throws IOException{
        Path folderLocation= rootLocation.resolve(stringsubfolder);
        // Lo crea si el directorio no existe
        Files.createDirectories(folderLocation);
        String filename = multipartFile.getOriginalFilename();
        Path destinationFile = folderLocation.resolve(
                Paths.get(filename))
                .normalize().toAbsolutePath();
        if(!destinationFile.getParent().equals(folderLocation.toAbsolutePath())){
            throw new FileStorageExeption("No se puede almacenar el archivo fuera del directorio especificado:" + filename);
        }

        Files.copy(multipartFile.getInputStream(), destinationFile);
        return destinationFile.toString();

    }

    // cargar un archivo especifico como recurso
    public Resource LoadFileAsResource(String path) {
        try {
            Path filePath = rootLocation.resolve(path).normalize();
            File file=filePath.toFile();
            Resource resource = new org.springframework.core.io.FileSystemResource(file);
            if(resource.exists()||resource.isReadable()){
                return resource;
            }else{
                throw new FileStorageExeption("No se pudo leer el archivo:"+ path);
            }
        }catch (Exception e){
            throw new FileStorageExeption("No se pudo leer el archivo:"+ path,e);
        }

    }


    public void deleteFile(String path) throws IOException {
        Path filePath = rootLocation.resolve(path).normalize();
        File file=filePath.toFile();
        if(file.exists()){
            file.delete();
        }else{
            throw new FileStorageExeption("No se pudo encontrar el archivo:"+ path);
        }
    }



}


 */

public interface FileStorageService {

    String storeFile(MultipartFile file, String subfolder) throws IOException;

    Resource loadFileAsResource(String path) throws MalformedURLException;

    void deleteFile(String path) throws IOException;
}
