package com.guarderia.gestion_guarderia.controller;
import com.guarderia.gestion_guarderia.dto.FotoDTO;
import com.guarderia.gestion_guarderia.service.FotoService;
import com.guarderia.gestion_guarderia.utils.constant.ApiConstantEndpoint;
import com.guarderia.gestion_guarderia.utils.constant.RoleConstant;
import lombok.AllArgsConstructor;

import lombok.NonNull;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping(ApiConstantEndpoint.API_FOTO)
@AllArgsConstructor
public class FotoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FotoController.class);
    private final FotoService fotoService;

    @PostMapping("/actividad/{actividadId}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<FotoDTO> uploadFoto(@PathVariable @NonNull Long actividadId, @RequestParam("file")MultipartFile file){
        LOGGER.info("Request recibida para subir foto");
        try {
            FotoDTO fotoDTO = fotoService.saveFoto(file, actividadId);
            LOGGER.info("Foto subida con id {}", fotoDTO.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(fotoDTO);
        } catch (IOException e) {
            LOGGER.warn("Error al subir foto {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/actividad/{actividadId}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<List<Resource>> getFotosByActividad(@PathVariable Long actividadId) {
        try {
            List<Resource> resources = fotoService.getFotosByActividadIdResource(actividadId);
            return ResponseEntity.ok().
                    header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resources.get(0).getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE)).body(resources);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
    public ResponseEntity<Void> deleteFoto(@PathVariable Long id) {
        try {
            fotoService.deleteFoto(id);
            return ResponseEntity.noContent().build();
        } catch (IOException e) {
            LOGGER.error("Error al eliminar la foto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
