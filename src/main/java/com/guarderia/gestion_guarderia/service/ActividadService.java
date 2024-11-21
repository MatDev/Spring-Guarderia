package com.guarderia.gestion_guarderia.service;

import com.guarderia.gestion_guarderia.dto.ActividadDTO;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;

import lombok.NonNull;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;



public interface ActividadService {

    List<ActividadDTO> getAllActividades();

    ActividadDTO getActividadById(@NonNull Long id) throws NotFoundExeption;

    List <ActividadDTO> getActividadesByCreadorId(@NonNull Long creadorId) throws NotFoundExeption;

    List <ActividadDTO> getActividadesByEncargadoId(@NonNull Long encargadoId) throws NotFoundExeption;

    @Transactional
    ActividadDTO createActividad(@NonNull ActividadDTO actividadDTO);

    @Transactional
    ActividadDTO updateActividad(@NonNull Long id, @NonNull ActividadDTO actividadDTO) throws NotFoundExeption;

    @Transactional
    void deleteActividad(@NonNull Long id) throws NotFoundExeption;

    List<ActividadDTO> getActividadesByAyudanteId(@NonNull Long ayudanteId) throws NotFoundExeption;


}
