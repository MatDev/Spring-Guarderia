package com.guarderia.gestion_guarderia.repository;

import com.guarderia.gestion_guarderia.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
 Usuario findByEmail(String email);
 Usuario findByNombre(String username);
 Usuario findByRut(String rut);
}