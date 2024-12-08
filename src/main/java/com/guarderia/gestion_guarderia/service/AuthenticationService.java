package com.guarderia.gestion_guarderia.service;

import com.guarderia.gestion_guarderia.dto.request.AuthenticationRequestDTO;
import com.guarderia.gestion_guarderia.dto.response.AuthenticationResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service

public interface AuthenticationService {
    AuthenticationResponseDTO login(AuthenticationRequestDTO request);
    void ReefreshToken(HttpServletRequest request , HttpServletResponse response);



}
