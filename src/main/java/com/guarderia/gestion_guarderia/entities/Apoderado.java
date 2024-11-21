package com.guarderia.gestion_guarderia.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Apoderado extends Usuario{
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "apoderado_parvulo",
        joinColumns = @JoinColumn(name = "apoderado_id"),
        inverseJoinColumns = @JoinColumn(name = "parvulo_id")
    )

    @JsonManagedReference
    private List<Parvulo> parvulos;

}
