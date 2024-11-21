package com.guarderia.gestion_guarderia.service;

import com.guarderia.gestion_guarderia.dto.AsistenteParvuloDTO;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/*
@Service
public class AsistenteParvuloService {
    @Autowired
    private AsistenteParvuloRepository asistenteParvuloRepository;

    //Crear un asisistente de parvulo
    public AsistenteParvulo createAsistenteParvulo(AsistenteParvulo asistenteParvulo){
        //asigno el rol
        asistenteParvulo.setRol(Rol.ASISTENTE_PARVULO);
        return asistenteParvuloRepository.save(asistenteParvulo);
    }

    //Obtener todos los asistentes de parvulo
    public List<AsistenteParvulo> getAllAsistentesParvulo(){
        return asistenteParvuloRepository.findAll();
    }

    //Obtener un asistente de parvulo por id
    public AsistenteParvulo getAsistenteParvuloById(Long id) {
        return asistenteParvuloRepository.findById(id).orElseThrow(()->new NotFoundExeption("Asistente de parvulo no encontrado con id "+id));
    }

    //Actualizar un asistente de parvulo
    public AsistenteParvulo updateAsistenteParvulo(Long id ,AsistenteParvulo asistenteParvuloActualizado){
        return asistenteParvuloRepository.findById(id)
                .map(asistenteParvulo -> {
                    asistenteParvulo.setNombre(asistenteParvuloActualizado.getNombre());
                    asistenteParvulo.setEmail(asistenteParvuloActualizado.getEmail());
                    asistenteParvulo.setRut(asistenteParvuloActualizado.getRut());
                    return asistenteParvuloRepository.save(asistenteParvulo);
                }).orElseThrow(()->new NotFoundExeption("Asistente de parvulo no encontrado con id "+id));

    }

}


 */

public interface AsistenteParvuloService {

    @Transactional
    AsistenteParvuloDTO createAsistenteParvulo(@NonNull AsistenteParvuloDTO asistenteParvuloDTO);

    @Transactional
    AsistenteParvuloDTO updateAsistenteParvulo(@NonNull Long id, AsistenteParvuloDTO asistenteParvuloDTO);

    @Transactional
    void deleteAsistenteParvulo(@NonNull Long id);

    AsistenteParvuloDTO getAsistenteParvuloById(@NonNull Long id) throws NotFoundExeption;

    List<AsistenteParvuloDTO> getAllAsistentesParvulo();


}