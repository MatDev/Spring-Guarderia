package com.guarderia.gestion_guarderia.utils.constant;

public class SwaggerConstant {
    private SwaggerConstant() {
        throw new IllegalStateException("Utility class");
    }
    private static final String[] SWAGGER_WHITE_LIST_URL = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs.yaml"
    };
}
