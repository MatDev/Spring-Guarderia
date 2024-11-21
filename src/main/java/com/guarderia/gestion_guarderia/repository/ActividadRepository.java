package com.guarderia.gestion_guarderia.repository;

import com.guarderia.gestion_guarderia.entities.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad,Long> {
    List<Actividad>  findByCreadorId(Long creadorId);
    List<Actividad> findByEncargadoId(Long encargadoId);
    List<Actividad> findByAyudantesId(Long ayudanteId);
}
