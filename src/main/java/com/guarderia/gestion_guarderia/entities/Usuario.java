package com.guarderia.gestion_guarderia.entities;

import com.guarderia.gestion_guarderia.utils.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
/*
La clase usuario se implementa de UserDetails para poder ser utilizada por Spring Security.
Se utiliza la anotacion @Inheritance para indicar que se va a utilizar una estrategia de herencia.
 */


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Usuario implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rol.getAuthorities();
    }

    @Override
    public String getUsername() {
        return rut;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
