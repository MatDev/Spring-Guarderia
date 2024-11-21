package com.guarderia.gestion_guarderia.repository;

import com.guarderia.gestion_guarderia.entities.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository <Foto,Long> {


    List<Foto> findByActividadId(Long actividadId);

}
