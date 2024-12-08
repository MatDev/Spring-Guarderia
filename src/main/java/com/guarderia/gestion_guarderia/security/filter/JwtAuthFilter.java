package com.guarderia.gestion_guarderia.security.filter;

import com.guarderia.gestion_guarderia.exception.ExpiredTokenException;
import com.guarderia.gestion_guarderia.exception.InvalidTokenException;
import com.guarderia.gestion_guarderia.repository.TokenRepository;
import com.guarderia.gestion_guarderia.security.service.JwtService;

import com.guarderia.gestion_guarderia.utils.constant.ApiConstantEndpoint;
import com.guarderia.gestion_guarderia.utils.constant.AuthConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JwtAuthFilter.class);
    private final JwtService jwtService;
    /*
     * UserDetailsService es una interfaz que Spring Security proporciona para recuperar los detalles del usuario.
     */
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        LOGGER.debug("Procesando Request : {}",request.getServletPath());
        if(isAuthPath(request)){
          LOGGER.debug("Saltar la autenticación JWT para la ruta de autenticación");
          filterChain.doFilter(request,response);
            return;
        }
        LOGGER.debug("Procesando autenticación JWT para la ruta: {}",request.getServletPath());
        final String AuthHeader=request.getHeader(AuthConstant.AUTHORIZATION_HEADER);
        if(isInvalidAuthHeader(AuthHeader)){
            LOGGER.warn("Cabecera de autorización no válida, procediendo al filtro de autorizacion");
            filterChain.doFilter(request,response);
            return;
        }

        final String jwt = extractJwtFromHeader(AuthHeader);
        final String userRut;
        try {
            userRut = jwtService.extractUsername(jwt);
        } catch (ExpiredTokenException e) {
            LOGGER.warn("Error al extraer el nombre de usuario del token:" + e.getMessage());
            filterChain.doFilter(request,response);
            return;
        } catch (InvalidTokenException e) {
            LOGGER.warn("Token invalido:" + e.getMessage());
            filterChain.doFilter(request,response);
            return;

        }


        if(userRut!=null && isNotAuthenticated(userRut)){
            LOGGER.warn("Usuario no autenticado");
            processTokenAuthetication(request,jwt,userRut);
        }
        filterChain.doFilter(request,response);
    }




    private boolean isAuthPath(HttpServletRequest request) {
            return request.getServletPath().equals(ApiConstantEndpoint.API_AUTH + "/login");
            /*
            Se puede añadir registro pero, para este caso la unica que puede registrar usuarios es la parvularia
            porlotanto no es necesario
             */
    }
    private boolean isInvalidAuthHeader(String authHeader) {
        return authHeader == null || !authHeader.startsWith(AuthConstant.BEARER_PREFIX);
    }

    private String extractJwtFromHeader(String authHeader) {
        return authHeader.substring(AuthConstant.BEARER_PREFIX.length());
    }
    private boolean isNotAuthenticated(String userRut) {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void processTokenAuthetication(HttpServletRequest request, String jwt, String userRut) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userRut);
        Boolean isTokenValid=tokenRepository.findByAccessToken(jwt)
                .map(t->!t.isExpirado() && !t.isRevocado()).orElse(false);

        logger.debug("Procesando autenticación de token para el usuario"+userRut);
        logger.debug("Token valido: "+isTokenValid);

        if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
            UsernamePasswordAuthenticationToken authtoken= new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authtoken);
            logger.debug("Authentication set in SecurityContext for user: " + userRut);
        } else {
            logger.debug("Token validation failed for user: " + userRut);
        }
    }

    private void handleException(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType(AuthConstant.CONTENT_TYPE_JSON);
        response.getWriter().write("{\"error\": \"" + message + "\"}");
        response.getWriter().flush();
    }









}
