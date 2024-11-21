package com.guarderia.gestion_guarderia.exception;

import lombok.Data;

@Data
public class InternalServerErrorExeption extends RuntimeException {
    private String code;
    private String message;

    public InternalServerErrorExeption(String message) {
        super(message);
    }
}
