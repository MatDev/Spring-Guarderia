package com.guarderia.gestion_guarderia.service.impl;

import com.guarderia.gestion_guarderia.dto.ParvulariaDTO;
import com.guarderia.gestion_guarderia.entities.Parvularia;

import com.guarderia.gestion_guarderia.utils.enums.Rol;
import com.guarderia.gestion_guarderia.exception.InternalServerErrorExeption;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.repository.ParvulariaRepository;
import com.guarderia.gestion_guarderia.service.ParvulariaService;
import com.guarderia.gestion_guarderia.utils.PasswordGenerator;
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
public class ParvulariaServiceImpl implements ParvulariaService {

    private final ModelMapper modelMapper;

    private final ParvulariaRepository parvulariaRepository;


    public static final Logger LOGGER = LoggerFactory.getLogger(ParvulariaServiceImpl.class);

    @Override
    @Transactional
    public ParvulariaDTO updateParvularia(@NonNull Long id, @NonNull ParvulariaDTO parvulariaDTO){
        LOGGER.info("Actualizando parvularia por id {}", id);
        Parvularia parvularia = parvulariaRepository.findById(id).orElseThrow(
                ()->{
                    LOGGER.warn("Parvularia no encontrada con id {} para actualizar", id);
                    return new NotFoundExeption("Parvularia no encontrada con id "+id);
                });
        parvularia.setNombre(parvulariaDTO.getNombre());
        parvularia.setRut(parvulariaDTO.getRut());
        parvularia.setEmail(parvulariaDTO.getEmail());

        try {
            parvularia = parvulariaRepository.save(parvularia);
            LOGGER.info("Parvularia actualizada con id {}", parvularia.getId());
            return convertToDto(parvularia);
        }catch (Exception e){
            LOGGER.error("Error al actualizar parvularia por id {}", id);
            throw new InternalServerErrorExeption("Error al actualizar parvularia por id "+id);
        }

    }
    @Override
    @Transactional
    public void deleteParvularia(@NonNull Long id){

        LOGGER.info("Eliminando parvularia por id {}", id);
        try {
            parvulariaRepository.deleteById(id);
            LOGGER.info("Parvularia eliminada por id {}", id);
        }catch (Exception e){
            LOGGER.error("Error al eliminar parvularia por id {}", id);
            throw new InternalServerErrorExeption("Error al eliminar parvularia por id "+id);
        }

    }
    @Override
    public ParvulariaDTO getParvulariaById(@NonNull Long id){
        LOGGER.info("Buscando parvularia por id {}", id);
        Parvularia parvularia = parvulariaRepository.findById(id).orElseThrow(
                ()->{
                    LOGGER.warn("Parvularia no encontrada con id {}", id);
                    return new NotFoundExeption("Parvularia no encontrada con id "+id);
                });
        LOGGER.info("Parvularia encontrada con id {}", id);
        return convertToDto(parvularia);
    }
    @Override
    public ParvulariaDTO getParvulariaByRut(@NonNull String rut){
        LOGGER.info("Buscando parvularia por rut {}", rut);
        Parvularia parvularia = parvulariaRepository.findByRut(rut).orElseThrow(
                ()->{
                    LOGGER.warn("Parvularia no encontrada con rut {}", rut);
                    return new NotFoundExeption("Parvularia no encontrada con rut "+rut);
                });
        LOGGER.info("Parvularia encontrada con rut {}", rut);
        return convertToDto(parvularia);

    }

    @Override
    public List<ParvulariaDTO> getAllParvularias(){
        LOGGER.info("Buscando todas las parvularias");
        List<Parvularia> parvularias = parvulariaRepository.findAll();
        LOGGER.info("Parvularias encontradas {}", parvularias.size());
        return parvularias.stream().map(
                this::convertToDto
        ).collect(Collectors.toList());

    }

    private ParvulariaDTO convertToDto(Parvularia parvularia){
        return modelMapper.map(parvularia, ParvulariaDTO.class);

    }

    private Parvularia convertToEntity(ParvulariaDTO parvulariaDTO){
        return modelMapper.map(parvulariaDTO, Parvularia.class);

    }



}
