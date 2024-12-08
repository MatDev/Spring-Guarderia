package com.guarderia.gestion_guarderia.configs;

import com.guarderia.gestion_guarderia.entities.Parvularia;

import com.guarderia.gestion_guarderia.repository.ParvulariaRepository;
import com.guarderia.gestion_guarderia.repository.UsuarioRepository;


import com.guarderia.gestion_guarderia.utils.enums.Rol;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor

public class DatabaseInitializer implements CommandLineRunner {
    private final ParvulariaRepository parvulariaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String ... args) throws Exception {
        Parvularia parvularia =Parvularia.builder()
                .nombre("admin")
                .rol(Rol.PARVULARIA)
                .rut("12345678-9")
                .email("admin@admin.com")
                .password(passwordEncoder.encode("admin"))
                .build();
        parvulariaRepository.save(parvularia);


    }
}
