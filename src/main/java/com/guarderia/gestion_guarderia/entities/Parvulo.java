package com.guarderia.gestion_guarderia.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Parvulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    @NotNull(message = "El apellido es obligatorio")
    private String apellido;

    @Column(nullable = false, unique = true)
    @NotNull(message = "El Rut es obligatorio")
    private String rut;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private Date fechaNacimiento;

    @ManyToMany(mappedBy = "parvulos")
    @JsonIgnoreProperties("parvulos")
    @JsonBackReference
    private List<Apoderado> apoderados;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parvularia_id", nullable = false)

    @NotNull(message = "La parvularia es obligatoria")
    private Parvularia parvularia;



}
