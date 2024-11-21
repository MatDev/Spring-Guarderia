package com.guarderia.gestion_guarderia.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    @Bean
    public String passwordEncoder() {
        return "passwordEncoder";
    }
}
