package com.guarderia.gestion_guarderia.exception;

public class FileStorageExeption extends RuntimeException{
    public FileStorageExeption(String message) {
        super(message);
    }
    public FileStorageExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
