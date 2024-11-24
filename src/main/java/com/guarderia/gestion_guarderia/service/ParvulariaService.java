package com.guarderia.gestion_guarderia.service;


import com.guarderia.gestion_guarderia.dto.ParvulariaDTO;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;

import lombok.NonNull;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ParvulariaService {

    @Transactional
    ParvulariaDTO updateParvularia(@NonNull Long id, @NonNull ParvulariaDTO parvulariaDTO);

    @Transactional
    void deleteParvularia(@NonNull Long id);

    ParvulariaDTO getParvulariaById(@NonNull Long id) throws NotFoundExeption;

    ParvulariaDTO getParvulariaByRut(@NonNull String rut) throws NotFoundExeption;

    List<ParvulariaDTO> getAllParvularias();


}