package com.guarderia.gestion_guarderia.repository;

import com.guarderia.gestion_guarderia.entities.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia,Long> {
    List<Asistencia> findByParvuloId(Long parvuloId);
    List<Asistencia> findByActividadId(Long actividadId);
    // bucar por parvulo y actividad id
    Asistencia findByParvuloIdAndActividadId(Long parvuloId, Long actividadId);

}
