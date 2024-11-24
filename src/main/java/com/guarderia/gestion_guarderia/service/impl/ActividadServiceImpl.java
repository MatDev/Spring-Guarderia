package com.guarderia.gestion_guarderia.service.impl;


import com.guarderia.gestion_guarderia.dto.ActividadDTO;

import com.guarderia.gestion_guarderia.exception.InternalServerErrorExeption;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.entities.Actividad;
import com.guarderia.gestion_guarderia.repository.ActividadRepository;
import com.guarderia.gestion_guarderia.service.ActividadService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ActividadServiceImpl implements ActividadService{
    public static final Logger LOGGER = LoggerFactory.getLogger(ActividadServiceImpl.class);

    private final ActividadRepository actividadRepository;

    private final ModelMapper modelMapper;

   //Listo todas las actividades
    @Override
    public List<ActividadDTO> getAllActividades(){
        LOGGER.info("Buscando todas las actividades");
        List<Actividad> actividades = actividadRepository.findAll();
        LOGGER.info("Se encontraron {} actividades", actividades.size());
        return actividades.stream().map(
                this::convertToDto
        ).toList();
    }
    //Obtengo una actividad por id

    @Override
    public ActividadDTO getActividadById(@NonNull Long id){
        LOGGER.info("Buscando actividad por id {}", id);
        Actividad actividad = actividadRepository.findById(id).orElseThrow(
                ()->{
                    LOGGER.warn("Actividad no encontrada con id {}", id);
                    return new  NotFoundExeption("Actividad no encontrada con id "+id);
                });
        LOGGER.info("Actividad encontrada con id {}", id);
        return convertToDto(actividad);

    }
    //Obtengo todas las actividades de un creador
    @Override
    public List <ActividadDTO> getActividadesByCreadorId(@NonNull Long creadorId){
        LOGGER.info("Buscando actividades por creador id {}", creadorId);
        List<Actividad> actividades =actividadRepository.findByCreadorId(creadorId);
        return actividades.stream().map(
                this::convertToDto
        ).toList();

    }

    @Override
    public List <ActividadDTO> getActividadesByEncargadoId(@NonNull Long encargadoId){
        LOGGER.info("Buscando actividades por encargado id {}", encargadoId);
        List<Actividad> actividades =actividadRepository.findByEncargadoId(encargadoId);
        return actividades.stream().map(
                this::convertToDto
        ).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public ActividadDTO createActividad(@NonNull ActividadDTO actividadDTO){
        LOGGER.info("Creando actividad");
        try {
            Actividad actividad = convertToEntity(actividadDTO);
            actividad = actividadRepository.save(actividad);
            LOGGER.info("Actividad creada con id {}", actividad.getId());
            return convertToDto(actividad);
        }catch (Exception e){
            LOGGER.error("Error al crear actividad", e);
            throw new InternalServerErrorExeption("Error al crear actividad "+e.getMessage());
        }
    }
    @Override
    @Transactional
    public ActividadDTO updateActividad(@NonNull Long id, @NonNull ActividadDTO actividadDTO){
        LOGGER.info("Actualizando actividad con id {}", id);
        Actividad actividad = actividadRepository.findById(id).orElseThrow(
                ()->{
                    LOGGER.warn("Actividad no encontrada con id {} para actualizar", id);
                    return new NotFoundExeption("Actividad no encontrada con id:" +id +" para actualizar");
                });
        Actividad actividadActualizada=convertToEntity(actividadDTO);
        actividadActualizada.setId(actividad.getId());
        actividadActualizada.setCreador(actividad.getCreador());
        Actividad updatedActividad = actividadRepository.save(actividadActualizada);

        LOGGER.info("Actividad actualizada con id {}", id);
        return convertToDto(updatedActividad);
    }
    @Override
    @Transactional
    public void deleteActividad(@NonNull Long id){
        LOGGER.info("Eliminando actividad con id {}", id);
        Actividad actividad =actividadRepository.findById(id).orElseThrow(
                ()->{
                    LOGGER.warn("No se encontro la actividad con id {} al eliminar", id);
                   return new NotFoundExeption("Actividad no encontrada con id: "+id +" para eliminar");
                });
        actividadRepository.delete(actividad);
        LOGGER.info("Actividad eliminada con id {}", id);
    }

    @Override
    public List<ActividadDTO> getActividadesByAyudanteId(@NonNull Long ayudanteId){
        LOGGER.info("Buscando actividades por ayudante id {}", ayudanteId);
        List<Actividad> actividades =actividadRepository.findByAyudantesId(ayudanteId);
        LOGGER.info("Se encontraron {} actividades para el ayudante {}", actividades.size(), ayudanteId);
        return actividades.stream().map(
                this::convertToDto
        ).collect(Collectors.toList());

    }



    private ActividadDTO convertToDto(Actividad actividad){
        return modelMapper.map(actividad, ActividadDTO.class);
    }

    private Actividad convertToEntity(ActividadDTO actividadDTO){
        return modelMapper.map(actividadDTO, Actividad.class);
    }



}
