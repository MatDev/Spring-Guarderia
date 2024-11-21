package com.guarderia.gestion_guarderia.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
    @JsonProperty("token")
    private String token;
    @JsonProperty("refreshToken")
    private String refreshToken;

}
