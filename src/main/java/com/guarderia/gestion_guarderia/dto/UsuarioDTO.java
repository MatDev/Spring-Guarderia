package com.guarderia.gestion_guarderia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.guarderia.gestion_guarderia.utils.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class UsuarioDTO {
    private Long id;


    private String rut;


    private String nombre;


    private String email;

    @JsonIgnore
    private String password;

    private Rol rol; // Representado como String para el enum
}
