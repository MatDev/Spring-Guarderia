package com.guarderia.gestion_guarderia.service.impl;

import com.guarderia.gestion_guarderia.dto.ApoderadoDTO;
import com.guarderia.gestion_guarderia.dto.ParvuloDTO;
import com.guarderia.gestion_guarderia.entities.Apoderado;
import com.guarderia.gestion_guarderia.entities.Parvulo;
import com.guarderia.gestion_guarderia.entities.Rol;
import com.guarderia.gestion_guarderia.exception.InternalServerErrorExeption;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.repository.ApoderadoRepository;
import com.guarderia.gestion_guarderia.repository.ParvuloRepository;
import com.guarderia.gestion_guarderia.service.ApoderadoService;
import com.guarderia.gestion_guarderia.service.ParvuloService;
import com.guarderia.gestion_guarderia.utils.PasswordGenerator;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApoderadoServiceImpl implements ApoderadoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApoderadoServiceImpl.class);
    private final ApoderadoRepository apoderadoRepository;
    private final ParvuloRepository parvuloRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ApoderadoDTO createApoderado(@NonNull ApoderadoDTO apoderadoDTO) {
        LOGGER.info("Creando apoderado");
        try {
            Apoderado apoderado = convertToEntity(apoderadoDTO);
            apoderado.setRol(Rol.APODERADO);
            apoderado.setPassword(PasswordGenerator.generatePassword(apoderadoDTO.getNombre(), apoderadoDTO.getRut()));
            apoderado = apoderadoRepository.save(apoderado);
            LOGGER.info("Apoderado creado con id {}", apoderado.getId());
            return convertToDto(apoderado);

        } catch (Exception e) {
            LOGGER.error("Error al crear apoderado");
            throw new InternalServerErrorExeption("Error al crear apoderado");

        }

    }

    @Override
    @Transactional
    public ApoderadoDTO updateApoderado(@NonNull Long id, @NonNull ApoderadoDTO apoderadoDTO) {
        LOGGER.info("Actualizando apoderado por id {}", id);
        Apoderado apoderado = apoderadoRepository.findById(id).orElseThrow(()->
        {   LOGGER.warn("Apoderado con id {} no se encontro para actualizar", id);
            return new NotFoundExeption("Apoderado no encontrado con id "+id);
        });
        apoderado.setNombre(apoderadoDTO.getNombre());
        apoderado.setRut(apoderadoDTO.getRut());
        apoderado.setEmail(apoderadoDTO.getEmail());

        List<Parvulo> parvuloList= apoderadoDTO.getParvulos().stream().map(
                parvuloDTO -> parvuloRepository.findById(parvuloDTO.getId()).orElseThrow(
                        ()->
                        {    LOGGER.warn("Parvulo con id {} no se encontro para actualizar el apoderado {}", parvuloDTO.getId(), id);
                            return new NotFoundExeption("Parvulo no encontrado con id "+parvuloDTO.getId());
                        })
        ).collect(Collectors.toList());
        apoderado.setParvulos(parvuloList);

        apoderado.setRol(Rol.APODERADO);
        try {
            apoderado = apoderadoRepository.save(apoderado);
            LOGGER.info("Apoderado actualizado con id {}", apoderado.getId());
            return convertToDto(apoderado);
        }catch (Exception e){
            LOGGER.error("Error al actualizar apoderado por id {}", id);
            throw new InternalServerErrorExeption("Error al actualizar apoderado por id "+id);

        }
    }

    @Override
    public void deleteApoderado(@NonNull Long id) {
        LOGGER.info("Eliminando apoderado por id {}", id);
        try {
            Apoderado apoderado = apoderadoRepository.findById(id).orElseThrow(
                    ()->{
                        LOGGER.warn("Apoderado con id {} no se encontro para eliminar", id);
                        return new NotFoundExeption("Apoderado no encontrado con id "+id);
                    });
            apoderadoRepository.deleteById(apoderado.getId());
            LOGGER.info("Apoderado eliminado con id {}", id);
        }catch (Exception e){
            LOGGER.error("Error al eliminar apoderado por id {}", id);
            throw new InternalServerErrorExeption("Error al eliminar apoderado por id "+id);
        }

    }

    @Override
    public ApoderadoDTO getApoderadoById(@NonNull Long id) throws NotFoundExeption {
        Apoderado apoderado = apoderadoRepository.findById(id).orElseThrow(()->new NotFoundExeption("Apoderado no encontrado con id "+id));
        return convertToDto(apoderado);
    }

    @Override
    public ApoderadoDTO getApoderadoByRut(@NonNull String rut) {
        LOGGER.info("Buscando apoderado por rut {}", rut);
        Apoderado apoderado = apoderadoRepository.findByRut(rut)
                .orElseThrow(
                        ()->{
                            LOGGER.warn("Apoderado no encontrado con rut {}", rut);
                            return new NotFoundExeption("Apoderado no encontrado con rut "+rut);
                        });
        LOGGER.info("Apoderado encontrado con rut {}", rut);
        return convertToDto(apoderado);
    }

    @Override
    public List<ApoderadoDTO> getAllApoderados() {
        LOGGER.info("Obteniendo todos los apoderados");
        List<Apoderado> apoderados =apoderadoRepository.findAll();

        LOGGER.info("Se encontraron {} apoderados", apoderados.size());
        return apoderados.stream().map(
                this::convertToDto
        ).toList();

    }




    private ApoderadoDTO convertToDto(Apoderado apoderado) {
        return modelMapper.map(apoderado, ApoderadoDTO.class);
    }

    private Apoderado convertToEntity(ApoderadoDTO apoderadoDTO) {
        return modelMapper.map(apoderadoDTO, Apoderado.class);
    }
}
