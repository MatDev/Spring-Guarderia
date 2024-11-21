package com.guarderia.gestion_guarderia.service.impl;
import com.guarderia.gestion_guarderia.dto.ParvulariaDTO;
import com.guarderia.gestion_guarderia.dto.ParvuloDTO;
import com.guarderia.gestion_guarderia.entities.Parvularia;
import com.guarderia.gestion_guarderia.entities.Parvulo;
import com.guarderia.gestion_guarderia.exception.InternalServerErrorExeption;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.repository.ParvuloRepository;
import com.guarderia.gestion_guarderia.service.ParvulariaService;
import com.guarderia.gestion_guarderia.service.ParvuloService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@AllArgsConstructor

public class ParvuloServiceImpl implements ParvuloService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParvuloServiceImpl.class);
    private final ParvuloRepository parvuloRepository;
    private final ModelMapper modelMapper;
    private final ParvulariaService parvulariaService;

    @Override
    @Transactional
    public ParvuloDTO createParvulo(@NonNull ParvuloDTO parvuloDTO) {
        LOGGER.info("Creando parvulo {}", parvuloDTO);
        Parvulo parvulo = convertToEntity(parvuloDTO);
        ParvulariaDTO parvularia = parvulariaService.getParvulariaById(parvuloDTO.getParvularia().getId());
        parvulo.setParvularia(modelMapper.map(parvularia, Parvularia.class));

        try {
            parvulo = parvuloRepository.save(parvulo);
            LOGGER.info("Parvulo creado con id {}",parvulo.getId());
            return convertToDto(parvulo);
        } catch (Exception e) {
            LOGGER.error("Error al crear parvulo {}", e.getMessage());
            throw new InternalServerErrorExeption("Error al crear parvulo");
        }
    }

    @Override
    @Transactional
    public ParvuloDTO updateParvulo(@NonNull Long id, @NonNull ParvuloDTO parvuloDTO) {
        LOGGER.info("Actualizando parvulo por id {}", id);
        try {
            Parvulo parvulo=convertToEntity(parvuloDTO);
            parvulo.setId(id);
            parvulo = parvuloRepository.save(parvulo);
            LOGGER.info("Parvulo actualizado con id {}", parvulo.getId());
            return convertToDto(parvulo);
        }catch (Exception e){
            LOGGER.error("Error al actualizar parvulo por id {} con erro: {}", id, e.getMessage());
            throw new InternalServerErrorExeption("Error al actualizar parvulo por id "+id);

        }


    }

    @Override
    @Transactional
    public void deleteParvulo(@NonNull Long id) {
        LOGGER.info("Eliminando parvulo por id {}", id);
        Parvulo parvulo = parvuloRepository.findById(id).orElseThrow(
                ()->{
                    LOGGER.warn("Parvulo con id {} no se encontro para eliminar", id);
                    return new NotFoundExeption("Parvulo no encontrado con id "+id);
                });
        try {
            parvuloRepository.delete(parvulo);
            LOGGER.info("Parvulo eliminado con id {}", id);
        }catch (Exception e){
            LOGGER.error("Error al eliminar parvulo por id {}", id);
            throw new InternalServerErrorExeption("Error al eliminar parvulo por id "+id);
        }

    }

    @Override
    public List<ParvuloDTO> getAllParvulos() {
        LOGGER.info("Buscando todos los parvulos");
        List<Parvulo> parvulos = parvuloRepository.findAll();
        LOGGER.info("Parvulos encontrados {}", parvulos.size());
        return parvulos.stream().map(
                this::convertToDto
        ).toList();
    }

    @Override
    public ParvuloDTO getParvuloById(@NonNull Long id) {
        LOGGER.info("Buscando parvulo por id {}", id);
        Parvulo parvulo = parvuloRepository.findById(id).orElseThrow(()->{
            LOGGER.warn("Parvulo no encontrado con id {}", id);
            return new NotFoundExeption("Parvulo no encontrado con id "+id);
        });
        LOGGER.info("Parvulo encontrado con id {}", id);
        return convertToDto(parvulo);
    }

    @Override
    public ParvuloDTO getParvuloByRut(@NonNull String rut) {
        LOGGER.info("Buscando parvulo por rut {}", rut);
        Parvulo parvulo = parvuloRepository.findByRut(rut).orElseThrow(()->{
            LOGGER.warn("Parvulo no encontrado con rut {}", rut);
            return new NotFoundExeption("Parvulo no encontrado con rut "+rut);
        });
        LOGGER.info("Parvulo encontrado con rut {}", rut);
        return convertToDto(parvulo);
    }

    private ParvuloDTO convertToDto(Parvulo parvulo){
        return modelMapper.map(parvulo, ParvuloDTO.class);
    }

    private Parvulo convertToEntity(ParvuloDTO parvuloDTO){
        return modelMapper.map(parvuloDTO, Parvulo.class);
    }
}
