package com.guarderia.gestion_guarderia.security.service;

import com.guarderia.gestion_guarderia.exception.ExpiredTokenException;
import com.guarderia.gestion_guarderia.exception.InvalidTokenException;
import com.guarderia.gestion_guarderia.utils.constant.AuthConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;


    /*
    Extrae el nombre de usuario del token usando la clave subject del JWT.
    Llama a extractClaim para obtener este valor.
     */
    public String extractUsername(String token){
        LOGGER.info("Obteniendo rut desde token");
        return extractClaim(token, Claims::getSubject);
    }

    /*
    Es un metodo generico que permite extraer cualquier claim del token.
    Recibe una función claimsResolver que define cómo se extrae el dato (por ejemplo, getSubject para el nombre de usuario).
    Depende de extractAllClaims.
    Recibe una función claimsResolver que define cómo se extrae el dato (por ejemplo, getSubject para el nombre de usuario).
    Depende de extractAllClaims.
     */

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        LOGGER.info("Extrayendo claim desde token");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    /*
    Decodifica y valida el token.
    Extrae todas las reclamaciones (Claims) del token.
    Usa la clave secreta (secretKey) para verificar la firma.
     */

    private Claims extractAllClaims(String token) {
       try {
          LOGGER.info("Extrayendo todos los claims desde token");
           return Jwts
                   .parserBuilder()
                   .setSigningKey(getSignInKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();

       }catch (ExpiredJwtException e){
              LOGGER.warn("Token expirado");
              throw new ExpiredTokenException("Token expirado: "+e.getMessage());
       }catch (IllegalArgumentException e){
           LOGGER.warn("Invalid token: {}", e.getMessage());
              throw new InvalidTokenException("Token invalido: "+e.getMessage());
       }

    }
    /*
    Genera un token de acceso JWT.
    Añade el rol del usuario en las reclamaciones (ROLE_CLAIM).
    Llama a create token(Map, UserDetails) para construir el token con las reclamaciones.

     */
    public String generateToken(UserDetails userDetails){
        LOGGER.info("Generando token");
        Map<String,Object> claims = new HashMap<>();
        claims.put(AuthConstant.ROLE_CLAIM,userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).findFirst().orElseThrow(()->
                {
                    LOGGER.warn("No se encontro rol en el usuario");
                    return new RuntimeException("No se encontro rol en el usuario");
                }));
        return generateToken(claims,userDetails);
    }

    /*
        Genera un token de acceso utilizando:
    Reclamos adicionales (extraClaims).
    Tiempo de expiración definido por jwtExpiration.
    Llama a buildToken.
     */


    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        LOGGER.info("Generando token con claims adicionales");
        return  buildToken(extraClaims,userDetails,jwtExpiration);

    }


    /*
    Genera un token de actualización JWT.
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }
    /*
    Construye el token JWT con:
    Reclamos adicionales (extraClaims).
    Nombre de usuario (subject).
    Fechas de emisión y expiración.
    Firma digital utilizando la clave secreta y el algoritmo HS512.
     */

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long jwtExpiration) {
        LOGGER.info("Contruyendo toke para usuario {}", userDetails.getUsername());
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        LOGGER.info("Validando token");
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        LOGGER.info("Validando si token ha expirado");
        return extractExpiration(token).before(new Date());
    }



    /*
    Se extrae solo la expiracion del token y se devuelve como un objeto Date.
    a travez de extractClaim.
     */
    private Date extractExpiration(String token){
        LOGGER.info("Extrayendo fecha de expiracion desde token");
        return extractClaim(token, Claims::getExpiration);
    }


    /*
    Convierte la clave secreta en un objeto Key para firmar y verificar tokens.
    Decodifica la clave secreta desde su representación en Base64.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
