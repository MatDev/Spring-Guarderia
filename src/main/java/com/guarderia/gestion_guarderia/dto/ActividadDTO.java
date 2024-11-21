package com.guarderia.gestion_guarderia.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.guarderia.gestion_guarderia.entities.Parvularia;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ActividadDTO {
    private Long id;


    private String nombre;

    private String descripcion;


    private Date fechaRealizacion;

    private Date fechaCreacion;

    private ParvulariaDTO creador; // ID de la Parvularia creadora

    private ParvulariaDTO encargado; // ID de la Parvularia encargada

    private List<AsistenteParvuloDTO> ayudantes; // Lista de IDs de los ayudantes (Asistentes de Párvulos)



}
