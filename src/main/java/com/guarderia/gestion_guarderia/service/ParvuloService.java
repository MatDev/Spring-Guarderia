package com.guarderia.gestion_guarderia.service;

import com.guarderia.gestion_guarderia.dto.ParvuloDTO;

import lombok.NonNull;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;




public interface ParvuloService {
    @Transactional
    ParvuloDTO createParvulo(@NonNull ParvuloDTO parvuloDTO);

    @Transactional
    ParvuloDTO updateParvulo(@NonNull Long id, @NonNull ParvuloDTO parvuloDTO);

    @Transactional
    void deleteParvulo(@NonNull Long id);


    List<ParvuloDTO> getAllParvulos();

    ParvuloDTO getParvuloById(@NonNull Long id);

    ParvuloDTO getParvuloByRut(@NonNull String rut);



}
