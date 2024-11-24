package com.guarderia.gestion_guarderia.controller;

import com.guarderia.gestion_guarderia.dto.ActividadDTO;
import com.guarderia.gestion_guarderia.exception.NotFoundExeption;
import com.guarderia.gestion_guarderia.service.ActividadService;
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
@RequestMapping(ApiConstantEndpoint.API_ACTIVIDAD)
@AllArgsConstructor
public class ActividadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActividadController.class);
    private final ActividadService actividadService;

    /*
    Crear actividades
    Acccesos solo para parvularia puede crear
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
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
    /*
    Actualizar actividades por id
    Acceso solo para parvularia puede actualizar
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
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
    /*
    Eliminar actividades por id
    Acceso solo para parvularia puede eliminar
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
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
    /*
    Obtener actividades por id
    Acceso para parvularia y asistente parvulo
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "') or hasRole('" + RoleConstant.ASISTENTE_PARVULO + "')")
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

    /*
    Listar actividades por encargado (solo parvularias son encargados)
    Acceso solo para parvularia
     */
    @GetMapping("/encargado/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
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
    /*
    Listar actividades por creador
    Acceso solo para parvularia
     */
    @GetMapping("/creador/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "')")
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
    /*
    Listar todas las actividades
    Acceso para parvularia y asistente parvulo
     */
    @GetMapping
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "') or hasRole('" + RoleConstant.ASISTENTE_PARVULO + "')")
    public ResponseEntity<List<ActividadDTO>> getAllActividades(){
        LOGGER.info("Obteniendo todas las actividades");
        List<ActividadDTO> actividadDTOS=actividadService.getAllActividades();
        return ResponseEntity.ok(actividadDTOS);
    }
    /*
    Listar actividades por ayudantes
    Acceso para parvularia y asistente parvulo
     */
    @GetMapping("/ayudantes/{id}")
    @PreAuthorize("hasRole('" + RoleConstant.PARVULARIA + "') or hasRole('" + RoleConstant.ASISTENTE_PARVULO + "')")
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
