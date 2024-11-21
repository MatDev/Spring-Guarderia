package com.guarderia.gestion_guarderia.entities;

import com.guarderia.gestion_guarderia.utils.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true, name = "access_token")
    public String AccessToken;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;


    private boolean revocado;

    private boolean expirado;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
