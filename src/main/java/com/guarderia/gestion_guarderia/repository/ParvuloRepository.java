package com.guarderia.gestion_guarderia.repository;

import com.guarderia.gestion_guarderia.entities.Parvulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParvuloRepository extends JpaRepository<Parvulo,Long> {
    //obtengo un parvulo por su rut
   Optional <Parvulo> findByRut(String rut);


}
