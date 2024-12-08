package com.guarderia.gestion_guarderia.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    // Permisos para Parvularia
    PARVULARIA_READ("parvularia:read"),
    PARVULARIA_CREATE("parvularia:create"),
    PARVULARIA_UPDATE("parvularia:update"),
    PARVULARIA_DELETE("parvularia:delete"),

    // Permisos para Apoderado
    APODERADO_READ("apoderado:read"),
    APODERADO_CREATE("apoderado:create"),
    APODERADO_UPDATE("apoderado:update"),
    APODERADO_DELETE("apoderado:delete"),

    // Permisos para Asistente de Párvulo
    ASISTENTE_PARVULO_READ("asistente_parvulo:read"),
    ASISTENTE_PARVULO_CREATE("asistente_parvulo:create"),
    ASISTENTE_PARVULO_UPDATE("asistente_parvulo:update"),
    ASISTENTE_PARVULO_DELETE("asistente_parvulo:delete");
    @Getter
    private final String permission;
}
