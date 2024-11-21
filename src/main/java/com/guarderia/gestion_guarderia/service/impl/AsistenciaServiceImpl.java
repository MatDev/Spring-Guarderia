package com.guarderia.gestion_guarderia.service.impl;

import com.guarderia.gestion_guarderia.dto.AsistenciaDTO;

import com.guarderia.gestion_guarderia.entities.Actividad;
import com.guarderia.gestion_guarderia.entities.Asistencia;

import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.repository.ActividadRepository;
import com.guarderia.gestion_guarderia.repository.AsistenciaRepository;
import com.guarderia.gestion_guarderia.repository.ParvuloRepository;
import com.guarderia.gestion_guarderia.service.AsistenciaService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AsistenciaServiceImpl implements AsistenciaService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AsistenciaServiceImpl.class);

    private final AsistenciaRepository asistenciaRepository;
    private final ActividadRepository actividadRepository;
    private final ParvuloRepository parvuloRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public List<AsistenciaDTO> registerAsistencia(@NonNull Long actividadId, @NonNull List<AsistenciaDTO> asistenciaDTOList) {
       LOGGER.info("Registrando asistencia para la actividad con ID: {}", actividadId);
        Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(() -> new NotFoundExeption("No se encontró actividad con ID " + actividadId));

        List<Asistencia> asistencias = asistenciaDTOList.stream()
                .map(asistenciaDTO -> {
                    var asistencia = convertToEntity(asistenciaDTO);
                    asistencia.setActividad(actividad);
                    return asistencia;
                })
                .collect(Collectors.toList());

        asistencias = asistenciaRepository.saveAll(asistencias);

        return asistencias.stream().map(this::convertToDto).toList();
    }


    @Override
    public List<AsistenciaDTO> getAsistenciaByParvuloId(@NonNull Long parvuloId) {
        LOGGER.info("Obteniendo asistencias para el párvulo con ID: {}", parvuloId);
        List<Asistencia> asistencias = asistenciaRepository.findByParvuloId(parvuloId);

        if (asistencias.isEmpty()) {
            throw new NotFoundExeption("No se encontraron asistencias para el párvulo con ID " + parvuloId);
        }

        return asistencias.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<AsistenciaDTO> getAsistenciaByActividadId(@NonNull Long actividadId) {
        LOGGER.info("Obteniendo asistencias para la actividad con ID: {}", actividadId);
        List<Asistencia> asistencias = asistenciaRepository.findByActividadId(actividadId);

        if (asistencias.isEmpty()) {
            throw new NotFoundExeption("No se encontraron asistencias para la actividad con ID " + actividadId);
        }

        return asistencias.stream().map(this::convertToDto).toList();
    }

    @Override
    public AsistenciaDTO getAsistenciaByParvuloIdAndActividadId(@NonNull Long parvuloId, Long actividadId) {
        LOGGER.info("Obteniendo asistencia para el párvulo con ID {} y actividad con ID {}", parvuloId, actividadId);
        var asistencia = asistenciaRepository.findByParvuloIdAndActividadId(parvuloId, actividadId);

        if (asistencia == null) {
            throw new NotFoundExeption("No se encontró asistencia para el párvulo con ID " + parvuloId + " y actividad con ID " + actividadId);
        }

        return convertToDto(asistencia);
    }

    @Override
    public void deleteAsistencia(@NonNull Long id) {
        LOGGER.info("Eliminando asistencia con ID {}", id);

        if (!asistenciaRepository.existsById(id)) {
            throw new NotFoundExeption("No se encontró asistencia con ID " + id);
        }

        asistenciaRepository.deleteById(id);
        LOGGER.info("Asistencia eliminada exitosamente con ID {}", id);
    }

    private Asistencia convertToEntity(AsistenciaDTO asistenciaDTO) {
        return modelMapper.map(asistenciaDTO, Asistencia.class);
    }

    private AsistenciaDTO convertToDto(Asistencia asistencia) {
        return modelMapper.map(asistencia, AsistenciaDTO.class);
    }
}

