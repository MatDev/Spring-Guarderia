package com.guarderia.gestion_guarderia.repository;

import com.guarderia.gestion_guarderia.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    @Query("""
       select t from Token t
       where t.usuario.id = :userId and (t.expirado = false or t.revocado = false)
       """)


    List<Token> findAllValidTokenByUser(Long userId);
    Optional<Token> findByAccessToken(String accessToken);

}
