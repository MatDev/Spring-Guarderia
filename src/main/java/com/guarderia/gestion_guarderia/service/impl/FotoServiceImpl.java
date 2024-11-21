package com.guarderia.gestion_guarderia.service.impl;

import com.guarderia.gestion_guarderia.dto.FotoDTO;
import com.guarderia.gestion_guarderia.entities.Foto;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.repository.ActividadRepository;
import com.guarderia.gestion_guarderia.repository.FotoRepository;
import com.guarderia.gestion_guarderia.service.FileStorageService;
import com.guarderia.gestion_guarderia.service.FotoService;
import com.guarderia.gestion_guarderia.utils.constant.FotoFileConstant;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class FotoServiceImpl implements FotoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FotoServiceImpl.class);
    private final FotoRepository fotoRepository;
    private final ActividadRepository actividadRepository;
    private final FileStorageService fileStorageService;
    private final ModelMapper modelMapper;
    private final Path rootLocation= FotoFileConstant.ROOT_LOCATION;



    @Override
    @Transactional
    public FotoDTO saveFoto(MultipartFile file, Long actividadId) throws IOException {
        LOGGER.info("Guardando foto para la actividad con id {}", actividadId);

        try {
            var actividad = actividadRepository.findById(actividadId)
                    .orElseThrow(()->{
                        LOGGER.warn("Actividad no encontrada con id {} al guardar la foto", actividadId);
                        return new NotFoundExeption("Actividad no encontrada con id "+ actividadId);
                    });
            String path =fileStorageService.storeFile(file,rootLocation.toString()+"/"+actividadId);
            Foto foto = new Foto();
            foto.setUrl(path);
            foto.setActividad(actividad);
            foto.setType(file.getContentType());
            foto.setFecha(new Date());
            Foto savedFoto = fotoRepository.save(foto);
            LOGGER.info("Foto guardada con id {}", savedFoto.getId());

            return convertToDto(savedFoto);
        } catch (Exception e) {
            LOGGER.error("Error al guardar la foto {}" , e.getMessage());
            throw new IOException("Error al guardar la foto"+e.getMessage());
        }


    }

    @Override
    public List<Resource> getFotosByActividadIdResource(Long actividadId) {
        LOGGER.info("Obteniendo fotos para la actividad con id {}", actividadId);
        List<Foto> fotos = fotoRepository.findByActividadId(actividadId);
        LOGGER.info("S encontraron {} fotos para la actividad con id {}", fotos.size(), actividadId);
        return fotos.stream().map(
                foto -> {
                    try {
                        return fileStorageService.loadFileAsResource(foto.getUrl());
                    } catch (IOException e) {
                        LOGGER.error("Error al cargar la foto con id {}", foto.getId());
                        return null;
                    }
                }
        ).toList();

    }

    @Override
    public Resource getFotoByIdResource(Long id) throws IOException {
        Foto foto = fotoRepository.findById(id)
                .orElseThrow(
                        () ->{
                            LOGGER.warn("Foto no encontrada con id {}", id);
                            return new NotFoundExeption("Foto no encontrada con id " + id);
                        }
                );
        return fileStorageService.loadFileAsResource(foto.getUrl());
    }

    @Override
    public void deleteFoto(Long idFoto) throws IOException {
        Foto foto = fotoRepository.findById(idFoto)
                .orElseThrow(() -> new NotFoundExeption("Foto no encontrada con id " + idFoto));

        // Eliminar el archivo del sistema de archivos
        fileStorageService.deleteFile(foto.getUrl());

        // Eliminar la entidad Foto de la base de datos
        fotoRepository.delete(foto);
        LOGGER.info("Foto eliminada con id {}", idFoto);
    }



    // Métodos de conversión
    private FotoDTO convertToDto(Foto foto) {
        return modelMapper.map(foto, FotoDTO.class);
    }

    private Foto convertToEntity(FotoDTO fotoDTO) {
        return modelMapper.map(fotoDTO, Foto.class);
    }
}
