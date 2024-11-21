package com.guarderia.gestion_guarderia.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String type;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;
}
