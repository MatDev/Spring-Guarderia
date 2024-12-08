package com.guarderia.gestion_guarderia.controller;

import com.guarderia.gestion_guarderia.dto.request.AuthenticationRequestDTO;
import com.guarderia.gestion_guarderia.dto.response.AuthenticationResponseDTO;
import com.guarderia.gestion_guarderia.service.AuthenticationService;
import com.guarderia.gestion_guarderia.service.UserService;
import com.guarderia.gestion_guarderia.utils.constant.ApiConstantEndpoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstantEndpoint.API_AUTH)
@AllArgsConstructor
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login( @RequestBody AuthenticationRequestDTO request){
        LOGGER.info("Request recibida para login de : {}", request.getRut());
        AuthenticationResponseDTO response = authenticationService.login(request);
        LOGGER.info("Usuario logeado exitosamente : {}", request.getRut());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletResponse response, HttpServletRequest request){
        LOGGER.info("Request recibida para refrescar token de");
        authenticationService.ReefreshToken(request, response);
        LOGGER.info("Token refrescado exitosamente");

    }


}
