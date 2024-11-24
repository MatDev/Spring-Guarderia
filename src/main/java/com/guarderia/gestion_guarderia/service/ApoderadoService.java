package com.guarderia.gestion_guarderia.service;

import com.guarderia.gestion_guarderia.dto.ApoderadoDTO;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApoderadoService {




    @Transactional
    ApoderadoDTO updateApoderado(@NonNull Long id, @NonNull ApoderadoDTO apoderadoDTO);

    @Transactional
    void deleteApoderado(@NonNull Long id);

    ApoderadoDTO getApoderadoById(@NonNull Long id) throws NotFoundExeption;

    ApoderadoDTO getApoderadoByRut(@NonNull String rut) throws NotFoundExeption;

    List<ApoderadoDTO> getAllApoderados();


}
