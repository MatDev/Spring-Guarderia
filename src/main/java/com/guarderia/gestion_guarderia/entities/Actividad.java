package com.guarderia.gestion_guarderia.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "El nombre de la actividad es obligatorio")
    private String nombre;

    @Column(nullable = false)
    @NotEmpty(message = "La descripción de la actividad es obligatoria")
    private String descripcion;

    @Column(nullable = false , updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de realización es obligatoria")
    private Date fechaRealizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creador_id", nullable = false)
    private Parvularia creador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encargado_id", nullable = false)
    private Parvularia encargado;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="asistente_actividad",
            joinColumns = @JoinColumn(name = "actividad_id"),
            inverseJoinColumns = @JoinColumn(name = "asistente_id")
    )
    private List<AsistenteParvulo> ayudantes;


}
