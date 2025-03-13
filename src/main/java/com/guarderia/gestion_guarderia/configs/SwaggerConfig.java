package com.guarderia.gestion_guarderia.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String SECURITY_REFERENCE = "Bearer";
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Gestión Guardería API")
                        .description("API para la gestión de una guardería")
                        .version("1.0.0")
                )
                .addSecurityItem(
                        new SecurityRequirement().addList(SECURITY_REFERENCE))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_REFERENCE,new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                );
    }
}
