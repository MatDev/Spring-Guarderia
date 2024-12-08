package com.guarderia.gestion_guarderia.service.impl;

import com.guarderia.gestion_guarderia.dto.request.AuthenticationRequestDTO;
import com.guarderia.gestion_guarderia.dto.response.AuthenticationResponseDTO;
import com.guarderia.gestion_guarderia.entities.Token;
import com.guarderia.gestion_guarderia.entities.Usuario;
import com.guarderia.gestion_guarderia.repository.TokenRepository;
import com.guarderia.gestion_guarderia.repository.UsuarioRepository;
import com.guarderia.gestion_guarderia.security.service.JwtService;
import com.guarderia.gestion_guarderia.service.AuthenticationService;
import com.guarderia.gestion_guarderia.utils.constant.AuthConstant;
import com.guarderia.gestion_guarderia.utils.enums.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;

    @Override
    public AuthenticationResponseDTO login(AuthenticationRequestDTO request) {
        LOGGER.info("Autenticando usuario {}", request.getRut());
        Usuario usuario=usuarioRepository.findByRut(request.getRut())
                .orElseThrow(()->{
                    LOGGER.warn("Usuario con rut {} no encontrado", request.getRut());
                    return new BadCredentialsException("Bad credentials");
                });
        autheticatedUser(request.getRut(), request.getPassword());
        String jwtToken=jwtService.generateToken(usuario);
        String refreshToken=jwtService.generateRefreshToken(usuario);
        revokeAllUserTokens(usuario);
        saveUserToken(usuario, jwtToken);
        return buildAuthenticationResponse(jwtToken, refreshToken);

    }



    @Override
    public void ReefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        if(isInvalidAuthorizationHeader(authorizationHeader)){
            LOGGER.warn("Authorization header invalido");
            return;
        }
        String refreshToken=extractTokenFromHeader(authorizationHeader);
        String UserName=jwtService.extractUsername(refreshToken);

        if (UserName != null){
            processRefreshToken(refreshToken, UserName, response);
        }


    }

    private void processRefreshToken(String refreshToken, String userName, HttpServletResponse response) {
        Usuario usuario=usuarioRepository.findByRut(userName)
                .orElseThrow(()->{
                    LOGGER.warn("Usuario con rut {} no encontrado", userName);
                    return new BadCredentialsException("Bad credentials");
                });
        if(jwtService.isTokenValid(refreshToken,usuario)){
            String accessToken =jwtService.generateToken(usuario);
            revokeAllUserTokens(usuario);
            saveUserToken(usuario, accessToken);
        }

    }

    private void saveUserToken(Usuario usuario, String accessToken) {
        Token token = Token.builder()
                .accessToken(accessToken)
                .usuario(usuario)
                .tokenType(TokenType.BEARER)
                .expirado(false)
                .revocado(false)
                .build();
        try {
            tokenRepository.save(token);
        }catch (DataIntegrityViolationException e){
            // Si el token ya existe, se actualiza
            Token existingToken = tokenRepository.findByAccessToken(accessToken)
                    .orElseThrow(() -> new RuntimeException("Deberia haber token pero no se encontro"));
            existingToken.setExpirado(false);
            existingToken.setRevocado(false);
            tokenRepository.save(existingToken);
        }
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        return authorizationHeader.substring(AuthConstant.BEARER_PREFIX.length());
    }

    private boolean isInvalidAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader==null || !authorizationHeader.startsWith(AuthConstant.BEARER_PREFIX);
    }

    private void autheticatedUser(String rut, String password) {
       authenticationManager.authenticate(new
               UsernamePasswordAuthenticationToken(rut, password));
    }
    private AuthenticationResponseDTO buildAuthenticationResponse(String jwtToken, String refreshToken){
        return AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    private void revokeAllUserTokens(Usuario usuario) {
        var validUserTokens=tokenRepository.findAllValidTokenByUser(usuario.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token->{
            token.setRevocado(true);
            token.setExpirado(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
