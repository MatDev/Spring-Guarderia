package com.guarderia.gestion_guarderia.service;

import com.guarderia.gestion_guarderia.dto.ApoderadoDTO;
import com.guarderia.gestion_guarderia.dto.AsistenteParvuloDTO;
import com.guarderia.gestion_guarderia.dto.ParvulariaDTO;
import com.guarderia.gestion_guarderia.dto.ParvuloDTO;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    ApoderadoDTO createApoderado(@NonNull ApoderadoDTO apoderadoDTO);


    @Transactional
    AsistenteParvuloDTO createAsistenteParvulo(@NonNull AsistenteParvuloDTO asistenteParvuloDTO);

    @Transactional
    ParvulariaDTO createParvularia(@NonNull  ParvulariaDTO parvulariaDTO);

    @Transactional
    ParvuloDTO createParvulo(@NonNull ParvuloDTO parvuloDTO);

}
