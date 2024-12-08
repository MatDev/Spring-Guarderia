package com.guarderia.gestion_guarderia.security.service;

import com.guarderia.gestion_guarderia.repository.TokenRepository;
import com.guarderia.gestion_guarderia.utils.constant.AuthConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AuthConstant.AUTHORIZATION_HEADER);
        final String jwt;
        if(authHeader==null || !authHeader.startsWith(AuthConstant.BEARER_PREFIX)){
            return;
        }
        jwt=authHeader.substring(AuthConstant.BEARER_PREFIX.length());
        var storeToken=tokenRepository.findByAccessToken(jwt).orElse(null);
        if(storeToken!=null){
            storeToken.setExpirado(true);
            storeToken.setRevocado(true);
            tokenRepository.save(storeToken);
            SecurityContextHolder.clearContext();
        }
    }
}
