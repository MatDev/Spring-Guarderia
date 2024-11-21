package com.guarderia.gestion_guarderia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoDTO {
    private Long id;
    private String url;          // Ruta donde se almacena la foto
    private String nombre;       // Nombre del archivo de la foto
    private String type;         // Tipo MIME de la foto (image/jpeg, image/png, etc.)
    private Long actividadId;    // ID de la actividad asociada, si aplica

}
