package com.guarderia.gestion_guarderia.service.impl;

import com.guarderia.gestion_guarderia.dto.*;
import com.guarderia.gestion_guarderia.entities.*;
import com.guarderia.gestion_guarderia.exception.InternalServerErrorExeption;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.repository.ApoderadoRepository;
import com.guarderia.gestion_guarderia.repository.AsistenteParvuloRepository;
import com.guarderia.gestion_guarderia.repository.ParvulariaRepository;
import com.guarderia.gestion_guarderia.repository.ParvuloRepository;
import com.guarderia.gestion_guarderia.service.ApoderadoService;
import com.guarderia.gestion_guarderia.service.AsistenteParvuloService;
import com.guarderia.gestion_guarderia.service.ParvulariaService;
import com.guarderia.gestion_guarderia.service.UserService;
import com.guarderia.gestion_guarderia.utils.PasswordGenerator;
import com.guarderia.gestion_guarderia.utils.enums.Rol;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final ParvulariaRepository parvulariaRepository;
    private final ApoderadoRepository apoderadoRepository;
    private final AsistenteParvuloRepository asistenteParvuloRepository;
    private final ParvuloRepository parvuloRepository;
    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Transactional
    public ParvulariaDTO createParvularia(@NonNull ParvulariaDTO parvulariaDTO) {
        LOGGER.info("Creando parvularia");
        Parvularia parvularia = convertToEntity(parvulariaDTO);
        parvularia.setPassword(generatePassword(parvulariaDTO.getNombre(), parvulariaDTO.getRut()));
        parvularia.setRol(Rol.PARVULARIA);
        try {
            parvularia = parvulariaRepository.save(parvularia);
            LOGGER.info("Parvularia creada con id {}", parvularia.getId());
            return convertToDto(parvularia);
        } catch (Exception e) {
            LOGGER.error("Error al crear parvularia: {}", e.getMessage());
            throw new InternalServerErrorExeption("Error al crear parvularia");
        }
    }

    @Override
    @Transactional
    public ApoderadoDTO createApoderado(@NonNull ApoderadoDTO apoderadoDTO) {
        LOGGER.info("Creando apoderado");
        try {
            Apoderado apoderado = convertToEntity(apoderadoDTO);
            apoderado.setRol(Rol.APODERADO);
            apoderado.setPassword(generatePassword(apoderadoDTO.getNombre(), apoderadoDTO.getRut()));
            apoderado = apoderadoRepository.save(apoderado);
            LOGGER.info("Apoderado creado con id {}", apoderado.getId());
            return convertToDto(apoderado);
        } catch (Exception e) {
            LOGGER.error("Error al crear apoderado: {}", e.getMessage());
            throw new InternalServerErrorExeption("Error al crear apoderado");
        }
    }

    @Override
    @Transactional
    public AsistenteParvuloDTO createAsistenteParvulo(@NonNull AsistenteParvuloDTO asistenteParvuloDTO) {
        LOGGER.info("Creando asistente parvulo");
        try {
            AsistenteParvulo asistenteParvulo = convertToEntity(asistenteParvuloDTO);
            asistenteParvulo.setRol(Rol.ASISTENTE_PARVULO);
            asistenteParvulo.setPassword(generatePassword(asistenteParvuloDTO.getNombre(), asistenteParvuloDTO.getRut()));
            asistenteParvulo = asistenteParvuloRepository.save(asistenteParvulo);
            LOGGER.info("Asistente parvulo creado con id {}", asistenteParvulo.getId());
            return convertToDto(asistenteParvulo);
        } catch (Exception e) {
            LOGGER.error("Error al crear asistente parvulo: {}", e.getMessage());
            throw new InternalServerErrorExeption("Error al crear asistente parvulo");
        }
    }

    @Override
    @Transactional
    public ParvuloDTO createParvulo(@NonNull ParvuloDTO parvuloDTO) {
        LOGGER.info("Creando parvulo");
        Parvulo parvulo = convertToEntity(parvuloDTO);
        Parvularia parvularia = parvulariaRepository.findById(parvuloDTO.getParvularia().getId())
                .orElseThrow(() -> new NotFoundExeption("Parvularia no encontrada con id " + parvuloDTO.getParvularia().getId()));
        parvulo.setParvularia(parvularia);
        try {
            parvulo = parvuloRepository.save(parvulo);
            LOGGER.info("Parvulo creado con id {}", parvulo.getId());
            return convertToDto(parvulo);
        } catch (Exception e) {
            LOGGER.error("Error al crear parvulo: {}", e.getMessage());
            throw new InternalServerErrorExeption("Error al crear parvulo");
        }
    }

    private String generatePassword(String nombre, String rut) {
        return PasswordGenerator.generatePassword(nombre, rut);
    }

    private Parvularia convertToEntity(ParvulariaDTO parvulariaDTO) {
        return modelMapper.map(parvulariaDTO, Parvularia.class);
    }

    private Apoderado convertToEntity(ApoderadoDTO apoderadoDTO) {
        return modelMapper.map(apoderadoDTO, Apoderado.class);
    }

    private AsistenteParvulo convertToEntity(AsistenteParvuloDTO asistenteParvuloDTO) {
        return modelMapper.map(asistenteParvuloDTO, AsistenteParvulo.class);
    }

    private Parvulo convertToEntity(ParvuloDTO parvuloDTO) {
        return modelMapper.map(parvuloDTO, Parvulo.class);
    }

    private ParvulariaDTO convertToDto(Parvularia parvularia) {
        return modelMapper.map(parvularia, ParvulariaDTO.class);
    }

    private ApoderadoDTO convertToDto(Apoderado apoderado) {
        return modelMapper.map(apoderado, ApoderadoDTO.class);
    }

    private AsistenteParvuloDTO convertToDto(AsistenteParvulo asistenteParvulo) {
        return modelMapper.map(asistenteParvulo, AsistenteParvuloDTO.class);
    }

    private ParvuloDTO convertToDto(Parvulo parvulo) {
        return modelMapper.map(parvulo, ParvuloDTO.class);
    }
}

