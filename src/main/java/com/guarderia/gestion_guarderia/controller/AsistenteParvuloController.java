package com.guarderia.gestion_guarderia.controller;

import com.guarderia.gestion_guarderia.dto.AsistenteParvuloDTO;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.service.AsistenteParvuloService;
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
@RequestMapping(ApiConstantEndpoint.API_ASISTENTE_PARVULO)
@AllArgsConstructor
public class AsistenteParvuloController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsistenteParvuloController.class);
    private final AsistenteParvuloService asistenteParvuloService;



    @PutMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<AsistenteParvuloDTO> updateAsistenteParvulo(@NonNull @PathVariable Long id,@NonNull @Valid @RequestBody final AsistenteParvuloDTO asistenteParvuloDTO){
        LOGGER.info("Request recibida para actualizar asistente de parvulo con id {}", id);
        try {
            AsistenteParvuloDTO asistenteParvuloDTO1=asistenteParvuloService.updateAsistenteParvulo(id, asistenteParvuloDTO);
            LOGGER.info("Asistente parvulo actualizado con id {}", asistenteParvuloDTO1.getId());
            return ResponseEntity.ok(asistenteParvuloDTO1);
        }catch (NotFoundExeption e){
            LOGGER.warn("Datos de asistente parvulo invalidos {}",e.getMessage() );
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<Void> deleteAsistenteParvulo(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para eliminar asistente parvulo por id {}", id);
        try {
            asistenteParvuloService.deleteAsistenteParvulo(id);
            LOGGER.info("Asistente parvulo eliminado con id {}", id);
            return ResponseEntity.noContent().build();
        }catch (NotFoundExeption e){
            LOGGER.warn("Asistente parvulo no encontrado con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<AsistenteParvuloDTO> getAsistenteParvuloById(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para obtener asistente parvulo por id {}", id);
        try {
            AsistenteParvuloDTO asistenteParvuloDTO=asistenteParvuloService.getAsistenteParvuloById(id);
            return ResponseEntity.ok(asistenteParvuloDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Asistente parvulo no encontrado con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<List<AsistenteParvuloDTO>> getAllAsistenteParvulo(){
        LOGGER.info("Request recibida para obtener todos los asistentes de párvulos");
        try {
            List<AsistenteParvuloDTO> asistenteParvuloDTO=asistenteParvuloService.getAllAsistentesParvulo();
            return ResponseEntity.ok(asistenteParvuloDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Asistentes de párvulos no encontrados");
            return ResponseEntity.notFound().build();
        }
    }





}
