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
    ASISTENTE_PARVULO_DELETE("asistente_parvulo:delete"),

    // Permisos para Párvulo
    PARVULO_READ("parvulo:read"),
    PARVULO_CREATE("parvulo:create"),
    PARVULO_UPDATE("parvulo:update"),
    PARVULO_DELETE("parvulo:delete"),

    // Permisos para Actividades
    ACTIVIDAD_READ("actividad:read"),
    ACTIVIDAD_CREATE("actividad:create"),
    ACTIVIDAD_UPDATE("actividad:update"),
    ACTIVIDAD_DELETE("actividad:delete"),

    // Permisos para Asistencias
    ASISTENCIA_READ("asistencia:read"),
    ASISTENCIA_REGISTRAR("asistencia:registrar"),
    ASISTENCIA_UPDATE("asistencia:update"),
    ASISTENCIA_DELETE("asistencia:delete"),

    // Permisos para Fotos
    FOTO_UPLOAD("foto:upload"),
    FOTO_DELETE("foto:delete"),
    FOTO_READ("foto:read"),
    FOTO_DOWNLOAD("foto:download");

    @Getter
    private final String permission;
}
