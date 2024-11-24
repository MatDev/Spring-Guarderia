package com.guarderia.gestion_guarderia.controller;

import com.guarderia.gestion_guarderia.dto.ParvulariaDTO;
import com.guarderia.gestion_guarderia.entities.Parvularia;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.service.ParvulariaService;
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
@RequestMapping(ApiConstantEndpoint.API_PARVULARIA)
@AllArgsConstructor
public class ParvulariaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParvulariaController.class);
    private final ParvulariaService parvulariaService;


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<ParvulariaDTO> updateParvularia(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody final ParvulariaDTO parvulariaDTO){
        LOGGER.info("Request recibida para actualizar parvularia con id {}", id);
        try {
            ParvulariaDTO parvulariaDTO1=parvulariaService.updateParvularia(id, parvulariaDTO);
            LOGGER.info("Parvularia actualizado con id {}", parvulariaDTO1.getId());
            return ResponseEntity.ok(parvulariaDTO1);
        }catch (NotFoundExeption e){
            LOGGER.warn("Datos de parvularia invalidos {}",e.getMessage() );
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<Void> deleteParvularia(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para eliminar parvularia por id {}", id);
        try {
            parvulariaService.deleteParvularia(id);
            LOGGER.info("Parvularia eliminado con id {}", id);
            return ResponseEntity.noContent().build();
        }catch (NotFoundExeption e){
            LOGGER.warn("Parvularia no encontrado con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("id/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<ParvulariaDTO> getParvulariaById(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para obtener parvularia por id {}", id);
        try {
            ParvulariaDTO parvulariaDTO=parvulariaService.getParvulariaById(id);
            return ResponseEntity.ok(parvulariaDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Parvularia no encontrado con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/rut/{rut}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<ParvulariaDTO> getParvulariaByRut(@NonNull @PathVariable String rut){
        LOGGER.info("Request recibida para obtener parvularia por rut {}", rut);
        try {
            ParvulariaDTO parvulariaDTO=parvulariaService.getParvulariaByRut(rut);
            return ResponseEntity.ok(parvulariaDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Parvularia no encontrado con rut {}", rut);
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<List<ParvulariaDTO>> getAllParvularias(){
        LOGGER.info("Request recibida para obtener todas las parvularias");
        List<ParvulariaDTO> parvulariaDTOS=parvulariaService.getAllParvularias();
        return ResponseEntity.ok(parvulariaDTOS);
    }
}
