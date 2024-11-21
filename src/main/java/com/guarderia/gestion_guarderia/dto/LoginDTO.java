package com.guarderia.gestion_guarderia.dto;

import com.guarderia.gestion_guarderia.validation.Rut;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotEmpty(message = "El Rut es obligatorio")
    @Rut
    private String rut;

    @NotEmpty(message = "La contraseña es obligatoria")
    private String password;
}
