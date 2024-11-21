package com.guarderia.gestion_guarderia.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RutValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface Rut {
    String message() default "Rut inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
