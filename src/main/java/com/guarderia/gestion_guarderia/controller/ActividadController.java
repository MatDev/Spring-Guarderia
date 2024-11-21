package com.guarderia.gestion_guarderia.controller;

import com.guarderia.gestion_guarderia.dto.ActividadDTO;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.service.ActividadService;
import com.guarderia.gestion_guarderia.utils.constant.ApiConstantEndpoint;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(ApiConstantEndpoint.API_ACTIVIDAD)
@AllArgsConstructor
public class ActividadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActividadController.class);
    private final ActividadService actividadService;

    @PostMapping("/create")
    public ResponseEntity<ActividadDTO> createActividad(@NonNull @Valid @RequestBody final ActividadDTO actividadDTO){
        LOGGER.info("Request recibida para crear actividad");
        try {
            ActividadDTO actividadDTO1=actividadService.createActividad(actividadDTO);
            LOGGER.info("Actividad creado con id {}", actividadDTO1.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(actividadDTO1);
        }catch (IllegalArgumentException e){
            LOGGER.warn("Datos de actividad invalidos {}",e.getMessage() );
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActividadDTO> updateActividad(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody final ActividadDTO actividadDTO){
        LOGGER.info("Reuquest recibida para actualizar actividad con id {}", id);
        try {
            ActividadDTO actividadDTO1=actividadService.updateActividad(id, actividadDTO);
            LOGGER.info("Actividad actualizado con id {}", actividadDTO1.getId());
            return ResponseEntity.ok(actividadDTO1);
        }catch (NotFoundExeption e){
            LOGGER.warn("Datos de actividad invalidos {}",e.getMessage() );
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActividad(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para eliminar actividad por id {}", id);
        try {
            actividadService.deleteActividad(id);
            LOGGER.info("Actividad eliminado con id {}", id);
            return ResponseEntity.noContent().build();
        }catch (NotFoundExeption e){
            LOGGER.warn("Actividad no encontrado con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActividadDTO> getActividadById(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para buscar actividad por id {}", id);
        try {
            ActividadDTO actividadDTO=actividadService.getActividadById(id);
            return ResponseEntity.ok(actividadDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Actividad no encontrado con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/encargado/{id}")
    public ResponseEntity<List<ActividadDTO>> getActividadByEncargado(@NonNull @PathVariable Long id){
        LOGGER.info("Request recibida para buscar actividad por encargado {}", id);
        try {
            List<ActividadDTO> actividadDTO=actividadService.getActividadesByEncargadoId(id);
            return ResponseEntity.ok(actividadDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Actividad no encontrado con encargado {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/creador/{id}")
    public ResponseEntity<List<ActividadDTO>> getActividadByCreador(@NonNull @PathVariable Long id){
        LOGGER.info("Obteniendo actividad por creador {}", id);
        try {
            List<ActividadDTO> actividadDTO=actividadService.getActividadesByCreadorId(id);
            return ResponseEntity.ok(actividadDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Actividad no encontrado con creador {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ActividadDTO>> getAllActividades(){
        LOGGER.info("Obteniendo todas las actividades");
        List<ActividadDTO> actividadDTOS=actividadService.getAllActividades();
        return ResponseEntity.ok(actividadDTOS);
    }

    @GetMapping("/ayudantes/{id}")
    public ResponseEntity<List<ActividadDTO>> getActividadByAyudantes(@NonNull @PathVariable Long id){
        LOGGER.info("Obteniendo actividad por ayudantes {}", id);
        try {
            List<ActividadDTO> actividadDTO=actividadService.getActividadesByAyudanteId(id);
            return ResponseEntity.ok(actividadDTO);
        } catch (NotFoundExeption e) {
            LOGGER.warn("Actividad no encontrado con ayudantes {}", id);
            return ResponseEntity.notFound().build();
        }
    }








}
