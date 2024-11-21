package com.guarderia.gestion_guarderia.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RutValidator implements ConstraintValidator<Rut, String> {
    @Override
    public boolean isValid(final String rutValue, final ConstraintValidatorContext context) {
        return isValidFormat(rutValue);
    }

    private boolean isValidFormat(final String rut) {
        return rut.matches("^[0-9]+-[0-9kK]$");
    }
}
