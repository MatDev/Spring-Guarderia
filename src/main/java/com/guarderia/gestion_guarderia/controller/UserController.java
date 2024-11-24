package com.guarderia.gestion_guarderia.controller;


import com.guarderia.gestion_guarderia.dto.ApoderadoDTO;
import com.guarderia.gestion_guarderia.dto.AsistenteParvuloDTO;
import com.guarderia.gestion_guarderia.dto.ParvulariaDTO;
import com.guarderia.gestion_guarderia.dto.ParvuloDTO;
import com.guarderia.gestion_guarderia.service.UserService;
import com.guarderia.gestion_guarderia.utils.constant.ApiConstantEndpoint;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstantEndpoint.API_USER)
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/create/parvulo")
    public ResponseEntity<ParvuloDTO> createParvulo(@NonNull @Valid @RequestBody final ParvuloDTO parvuloDTO){
        LOGGER.info("Request recibida para crear parvulo");
        try {

            ParvuloDTO parvuloDTO1=userService.createParvulo(parvuloDTO);
            LOGGER.info("Parvulo creado con id {}", parvuloDTO1.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(parvuloDTO1);

        } catch (IllegalArgumentException e) {
            LOGGER.warn("Datos de parvulos invalidos {}",e.getMessage() );
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/create/asistente")
    public ResponseEntity<AsistenteParvuloDTO> createAsistenteParvulo(@NonNull @Valid @RequestBody final AsistenteParvuloDTO asistenteParvuloDTO){
        LOGGER.info("Request recibida para crear asistente parvulo");
        try {
            AsistenteParvuloDTO asistenteParvuloDTO1=userService.createAsistenteParvulo(asistenteParvuloDTO);
            LOGGER.info("Asistente parvulo creado con id {}", asistenteParvuloDTO1.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(asistenteParvuloDTO1);
        }catch (IllegalArgumentException e){
            LOGGER.warn("Datos de asistente parvulo invalidos {}",e.getMessage() );
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create/parvularia")
    public ResponseEntity<ParvulariaDTO> createParvularia(@NonNull @Valid @RequestBody final ParvulariaDTO parvulariaDTO){
        LOGGER.info("Request recibida para crear parvularia");
        try {

            ParvulariaDTO parvulariaDTO1=userService.createParvularia(parvulariaDTO);
            LOGGER.info("Parvularia creada con id {} ", parvulariaDTO1.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(parvulariaDTO1);
        }catch (IllegalArgumentException e){
            LOGGER.warn("Datos de parvularia invalidos {}",e.getMessage() );
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/create/apoderado")
    public ResponseEntity<ApoderadoDTO> createApoderado(@NonNull @Valid @RequestBody final ApoderadoDTO apoderadoDTO){
        LOGGER.info("Request recibida para crear apoderado");
        try {
            ApoderadoDTO apoderadoDTO1=userService.createApoderado(apoderadoDTO);
            LOGGER.info("Apoderado creado con id {}", apoderadoDTO1.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(apoderadoDTO1);
        }catch (IllegalArgumentException e){
            LOGGER.warn("Datos de apoderado invalidos {}",e.getMessage() );
            return ResponseEntity.badRequest().build();
        }
    }




}
