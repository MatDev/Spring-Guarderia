package com.guarderia.gestion_guarderia.utils.constant;

public class AuthConstant {


    private AuthConstant() {
        throw new UnsupportedOperationException("Es una clase de utilidad, no puede ser instanciada");
    }
    public static final String AUTHORIZATION_HEADER="Authorization";
    public static final String BEARER_PREFIX="Bearer ";
    public static final String ROLE_CLAIM="role";
    public static final String CONTENT_TYPE_JSON="application/json";
}
