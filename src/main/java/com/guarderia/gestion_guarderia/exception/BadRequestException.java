package com.guarderia.gestion_guarderia.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
