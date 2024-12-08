package com.guarderia.gestion_guarderia.service.impl;

import com.guarderia.gestion_guarderia.dto.AsistenteParvuloDTO;
import com.guarderia.gestion_guarderia.entities.AsistenteParvulo;
import com.guarderia.gestion_guarderia.utils.enums.Rol;
import com.guarderia.gestion_guarderia.exception.InternalServerErrorExeption;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.repository.AsistenteParvuloRepository;
import com.guarderia.gestion_guarderia.service.AsistenteParvuloService;
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
public class AsistenteParvuloServiceImpl implements AsistenteParvuloService {
    public static final Logger LOGGER = LoggerFactory.getLogger(AsistenteParvuloServiceImpl.class);
    private final AsistenteParvuloRepository asistenteParvuloRepository;
    private final ModelMapper modelMapper;




    @Override
    @Transactional
    public AsistenteParvuloDTO updateAsistenteParvulo(@NonNull Long id, AsistenteParvuloDTO asistenteParvuloDTO) {
        LOGGER.info("Actualizando asistente parvulo por id {}", id);
        AsistenteParvulo asistenteParvulo = asistenteParvuloRepository.findById(id).orElseThrow(
                ()->{
                    LOGGER.warn("Asistente parvulo con id {} no se encontro para actualizar", id);
                    return new NotFoundExeption("Asistente parvulo no encontrado con id "+id);
                });
        asistenteParvulo.setNombre(asistenteParvuloDTO.getNombre());
        asistenteParvulo.setRut(asistenteParvuloDTO.getRut());
        asistenteParvulo.setEmail(asistenteParvuloDTO.getEmail());

        try {
            asistenteParvulo = asistenteParvuloRepository.save(asistenteParvulo);
            LOGGER.info("Asistente parvulo actualizado con id {}", asistenteParvulo.getId());
            return convertToDto(asistenteParvulo);
        }catch (Exception e){
            LOGGER.error("Error al actualizar asistente parvulo por id {}", id);
            throw new InternalServerErrorExeption("Error al actualizar asistente parvulo por id "+id);
        }
    }

    @Override
    public void deleteAsistenteParvulo(@NonNull Long id) {
        LOGGER.info("Eliminando asistente parvulo por id {}", id);
        AsistenteParvulo asistenteParvulo = asistenteParvuloRepository.findById(id).orElseThrow(
                ()->{
                    LOGGER.warn("Asistente parvulo con id {} no se encontro para eliminar", id);
                    return new NotFoundExeption("No se encontro asistente de parvulo con id: "+id +" para eliminar");
                });
        try {
            asistenteParvuloRepository.delete(asistenteParvulo);
            LOGGER.info("Asistente parvulo eliminado con id {}", asistenteParvulo.getId());
        }catch (Exception e){
            LOGGER.error("Error al eliminar asistente parvulo por id {}", id);
            throw new InternalServerErrorExeption("Error al eliminar asistente parvulo por id "+id);
        }

    }

    @Override
    public AsistenteParvuloDTO getAsistenteParvuloById(@NonNull Long id) throws NotFoundExeption {
        LOGGER.info("Buscando asistente parvulo por id {}", id);
        AsistenteParvulo asistenteParvulo = asistenteParvuloRepository.findById(id).orElseThrow(
                ()->{
                    LOGGER.warn("Asistente parvulo no encontrado con id {}", id);
                    return new NotFoundExeption("Asistente parvulo no encontrado con id "+id);
                });
        return convertToDto(asistenteParvulo);
    }

    private AsistenteParvuloDTO convertToDto(AsistenteParvulo asistenteParvulo){
        return modelMapper.map(asistenteParvulo, AsistenteParvuloDTO.class);
    }
    private AsistenteParvulo convertToEntity(AsistenteParvuloDTO asistenteParvuloDTO){
        return modelMapper.map(asistenteParvuloDTO,AsistenteParvulo.class);
    }

   @Override
    public  List<AsistenteParvuloDTO> getAllAsistentesParvulo(){
        LOGGER.info("Obteniendo todos los asistentes de párvulos");
        List<AsistenteParvulo> asistenteParvuloList = asistenteParvuloRepository.findAll();
        LOGGER.info("Se encontraron {} asistentes de párvulos", asistenteParvuloList.size());
        return asistenteParvuloList.stream().map(this::convertToDto).collect(Collectors.toList());
   }

}
