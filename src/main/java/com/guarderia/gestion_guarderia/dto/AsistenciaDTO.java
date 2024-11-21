package com.guarderia.gestion_guarderia.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsistenciaDTO {
    private Long id;


    private ParvulariaDTO parvulo;         // ID del párvulo


    private ActividadDTO actividad;       // ID de la actividad

    private boolean estadoAsistencia;        // Indica si asistió o no
}
