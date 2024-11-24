package com.guarderia.gestion_guarderia.controller;


import com.guarderia.gestion_guarderia.dto.ApoderadoDTO;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.service.ApoderadoService;
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
@RequestMapping(ApiConstantEndpoint.API_APODERADO)
@AllArgsConstructor
public class ApoderadoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApoderadoController.class);
    private final ApoderadoService apoderadoService;




    @PutMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<ApoderadoDTO> updateApoderado(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody final ApoderadoDTO apoderadoDTO){
        LOGGER.info("Request recibida con id {}", id);
        try {
            ApoderadoDTO apoderadoDTO1=apoderadoService.updateApoderado(id, apoderadoDTO);
            LOGGER.info("Apoderado actualizado con id {}", apoderadoDTO1.getId());
            return ResponseEntity.ok(apoderadoDTO1);
        }catch (NotFoundExeption e){
            LOGGER.warn("Datos de apoderado invalidos {}",e.getMessage() );
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<Void> deleteApoderado(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para eliminar apoderado por id {}", id);
        try {
            apoderadoService.deleteApoderado(id);
            LOGGER.info("Apoderado eliminado con id {}", id);
            return ResponseEntity.noContent().build();
        }catch (NotFoundExeption e){
            LOGGER.warn("Apoderado no encontrado con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<ApoderadoDTO> getApoderadoById(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para obtener apoderado por id {}", id);
        try {
            ApoderadoDTO apoderadoDTO=apoderadoService.getApoderadoById(id);
            return ResponseEntity.ok(apoderadoDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Apoderado no encontrado con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<ApoderadoDTO> getApoderadoByRut(@NonNull @PathVariable String rut){
        LOGGER.info("Request recibida para obtener apoderado por rut {}", rut);
        try {
            ApoderadoDTO apoderadoDTO=apoderadoService.getApoderadoByRut(rut);
            return ResponseEntity.ok(apoderadoDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Apoderado no encontrado con rut {}", rut);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<List<ApoderadoDTO>> getApoderados(){
        LOGGER.info("Request recibida para obtener todos los apoderados");
        try {
            List<ApoderadoDTO> apoderadoDTO=apoderadoService.getAllApoderados();
            return ResponseEntity.ok(apoderadoDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Apoderados no encontrados");
            return ResponseEntity.notFound().build();
        }
    }




}
