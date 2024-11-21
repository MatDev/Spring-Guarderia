package com.guarderia.gestion_guarderia.entities;

import com.guarderia.gestion_guarderia.utils.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Data

public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    @NotNull(message = "El Rut es obligatorio")
    private String rut;
    @NotNull(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    @Email(message = "Debe proporcionar un email válido")
    @NotNull(message = "El email es obligatorio")
    private String email;


    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Token> tokens;

}
