package com.guarderia.gestion_guarderia.utils.constant;

public class ApiConstantEndpoint {
    private ApiConstantEndpoint() {
        throw new IllegalStateException("Utility class");
    }


    public static final String API_VERSION = "/api/v1";
    public static final String API_PARVULARIA = API_VERSION + "/parvularia";
    public static final String API_ASISTENTE_PARVULO = API_VERSION + "/asistente-parvulo";
    public static final String API_APODERADO = API_VERSION + "/apoderado";
    public static final String API_PARVULO = API_VERSION + "/parvulo";
    public static final String API_ACTIVIDAD = API_VERSION + "/actividad";
    public static final String API_ASISTENCIA = API_VERSION + "/asistencia";
    public static final String API_FOTO = API_VERSION + "/foto";
    public static final String API_USER = API_VERSION + "/user";

}
