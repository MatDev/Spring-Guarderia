package com.guarderia.gestion_guarderia.repository;

import com.guarderia.gestion_guarderia.entities.Parvularia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParvulariaRepository extends JpaRepository<Parvularia,Long> {
   Optional<Parvularia> findByRut(String rut);

}
