package com.guarderia.gestion_guarderia.service;

import com.guarderia.gestion_guarderia.dto.AsistenciaDTO;
import com.guarderia.gestion_guarderia.dto.ParvuloDTO;
import com.guarderia.gestion_guarderia.entities.Asistencia;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface AsistenciaService {
    @Transactional
    List<AsistenciaDTO> registerAsistencia(@NonNull Long actividadId,@NonNull List<AsistenciaDTO> asistenciaDTOList);

    List<AsistenciaDTO> getAsistenciaByParvuloId(@NonNull Long parvuloId);

    List<AsistenciaDTO> getAsistenciaByActividadId(@NonNull Long actividadId);

    AsistenciaDTO getAsistenciaByParvuloIdAndActividadId(@NonNull Long parvuloId, Long actividadId);

    void deleteAsistencia(@NonNull Long id);
}