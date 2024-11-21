package com.guarderia.gestion_guarderia.repository;

import com.guarderia.gestion_guarderia.entities.Apoderado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApoderadoRepository extends JpaRepository<Apoderado,Long> {

    Optional <Apoderado> findByRut(String rut);

}
