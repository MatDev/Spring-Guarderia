package com.guarderia.gestion_guarderia.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ParvuloDTO {
    private Long id;

    private String nombre;

    private String apellido;

    private String rut;

    private Date fechaNacimiento;

    private ParvulariaDTO parvularia;

}
