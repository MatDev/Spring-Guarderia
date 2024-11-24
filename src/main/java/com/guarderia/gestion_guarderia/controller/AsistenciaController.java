package com.guarderia.gestion_guarderia.controller;

import com.guarderia.gestion_guarderia.dto.AsistenciaDTO;
import com.guarderia.gestion_guarderia.dto.ParvuloDTO;
import com.guarderia.gestion_guarderia.service.AsistenciaService;
import com.guarderia.gestion_guarderia.utils.constant.ApiConstantEndpoint;
import com.guarderia.gestion_guarderia.utils.constant.RoleConstant;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullFields;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping(ApiConstantEndpoint.API_ASISTENCIA)
public class AsistenciaController {
    private final AsistenciaService asistenciaService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AsistenciaController.class);


    @PostMapping("/actividad/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "') or hasRole('" + RoleConstant.ASISTENTE_PARVULO + "')")
    public ResponseEntity<List<AsistenciaDTO>> registrarAsistencia(@NonNull @PathVariable Long id , @NonNull @RequestBody List<AsistenciaDTO> asistenciaDTOList){
        LOGGER.info("Request recibida para registrar asistencia");
        try {
            List<AsistenciaDTO> asistencia1 = asistenciaService.registerAsistencia(id, asistenciaDTOList);
            LOGGER.info("Asistencia registrada con id {}", id);
            return ResponseEntity.status(HttpStatus.CREATED).body(asistencia1);
        }catch (IllegalArgumentException e){
            LOGGER.warn("Datos de asistencia invalidos {}",e.getMessage() );
            return ResponseEntity.badRequest().build();
        }

    }

    /*
    Buscar asistencia por id de actividad
    acceso para parvularia y asistente de parvulo
     */

    @GetMapping("/actividad/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "') or hasRole('" + RoleConstant.ASISTENTE_PARVULO + "')")
    public ResponseEntity<List<AsistenciaDTO>> getAsistenciaByActividadId(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para buscar asistencia por id de actividad {}", id);
        try {
            List<AsistenciaDTO> listAsistencia = asistenciaService.getAsistenciaByActividadId(id);
            LOGGER.info("Asistencia encontrada con id {}", id);
            return ResponseEntity.status(HttpStatus.CREATED).body(listAsistencia);
        }catch (IllegalArgumentException e){
            LOGGER.warn("Datos de asistencia invalidos {}",e.getMessage() );
            return ResponseEntity.badRequest().build();
        }

    }
    /*
    Buscar asistencia por id de parvulo
    acceso para parvularia, asistente de parvulo y apoderado
     */
    @GetMapping("/parvulo/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "') or hasRole('" + RoleConstant.ASISTENTE_PARVULO + "') or hasRole('" + RoleConstant.APODERADO + "')")
    public ResponseEntity<List<AsistenciaDTO>> getAsistenciaByParvuloId(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para buscar asistencia por id de parvulo {}", id);
        try {
            List<AsistenciaDTO> asistencia1 = asistenciaService.getAsistenciaByParvuloId(id);
            LOGGER.info("Asistencia encontrada con id {}", id);
            return ResponseEntity.status(HttpStatus.CREATED).body(asistencia1);
        }catch (IllegalArgumentException e){
            LOGGER.warn("Datos de asistencia invalidos {}",e.getMessage() );
            return ResponseEntity.badRequest().build();
        }

    }

    /*
    Eliminar asistencia por id
    acceso solo para parvularia y asistente de parvulo
     */

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "') or hasRole('" + RoleConstant.ASISTENTE_PARVULO + "')")
    public ResponseEntity<Void> deleteAsistencia(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para eliminar asistencia por id {}", id);
        try {
            asistenciaService.deleteAsistencia(id);
            LOGGER.info("Asistencia eliminada con id {}", id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            LOGGER.warn("Asistencia no encontrada con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }





}
