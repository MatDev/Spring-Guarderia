package com.guarderia.gestion_guarderia.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean estadoAsistencia;


    @ManyToOne
    @JoinColumn(name="parvulo_id", nullable = false)
    @NotNull(message = "El ID del párvulo es obligatorio")
    private Parvulo parvulo;

    @ManyToOne
    @JoinColumn(name = "actividad_id", nullable = false)
    @NotNull(message = "El ID de la actividad es obligatorio")
    private Actividad actividad;

    @Column(nullable = false)
    @CreationTimestamp
    private Date fechaAsistencia;

}
