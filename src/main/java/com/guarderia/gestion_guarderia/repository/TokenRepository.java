package com.guarderia.gestion_guarderia.repository;

import com.guarderia.gestion_guarderia.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    @Query("""
           select t from Token t inner join Usuario u
            on t.userId = u.id
             where u.id = :userId and(t.expired = false or t.revoked = false)
           
           """)


    List<Token> findByUserId(Long userId);
    Optional<Token> findByAccessToken(String accessToken);

}
