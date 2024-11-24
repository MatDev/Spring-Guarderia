package com.guarderia.gestion_guarderia.controller;


import com.guarderia.gestion_guarderia.dto.ParvuloDTO;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.service.ParvuloService;
import com.guarderia.gestion_guarderia.utils.constant.ApiConstantEndpoint;
import com.guarderia.gestion_guarderia.utils.constant.RoleConstant;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstantEndpoint.API_PARVULO)
@AllArgsConstructor
public class ParvuloController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParvuloController.class);
    private final ParvuloService parvuloService;

    @GetMapping
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<List<ParvuloDTO>> getAllParvulos(){
        LOGGER.info("Reques recibida para obtener todos los parvulos");
        List<ParvuloDTO> parvuloDTOList=parvuloService.getAllParvulos();
        return ResponseEntity.ok(parvuloDTOList);
    }



    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<ParvuloDTO> getParvuloById(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para obtener parvulo por id {}", id);
        try {
            ParvuloDTO parvuloDTO=parvuloService.getParvuloById(id);
            return ResponseEntity.ok(parvuloDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Parvulo no encontrado con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<ParvuloDTO> getParvuloByRut(@NonNull @PathVariable String rut){
        LOGGER.info("Request recibida para obtener parvulo por rut {}", rut);
        try {
            ParvuloDTO parvuloDTO=parvuloService.getParvuloByRut(rut);
            return ResponseEntity.ok(parvuloDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Parvulo no encontrado con rut {}", rut);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<ParvuloDTO> updateParvulo(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody final ParvuloDTO parvuloDTO){
        LOGGER.info("Request recibida para actualizar parvulo con id {}", id);
        try {
            ParvuloDTO parvuloDTO1=parvuloService.updateParvulo(id, parvuloDTO);
            LOGGER.info("Parvulo actualizado con id {}", parvuloDTO1.getId());
            return ResponseEntity.ok(parvuloDTO1);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Parvulo no encontrado con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<Void> deleteParvulo(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para eliminar parvulo por id {}", id);
        try {
            parvuloService.deleteParvulo(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundExeption e) {
            LOGGER.warn("Parvulo no encontrado con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

}
