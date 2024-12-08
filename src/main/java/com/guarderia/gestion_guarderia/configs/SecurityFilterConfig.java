package com.guarderia.gestion_guarderia.configs;

import com.guarderia.gestion_guarderia.security.filter.JwtAuthFilter;
import com.guarderia.gestion_guarderia.utils.constant.ApiConstantEndpoint;
import com.guarderia.gestion_guarderia.utils.constant.RoleConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    private static final String[] WHITE_LIST_URL = {
            ApiConstantEndpoint.API_AUTH+"/login",
            ApiConstantEndpoint.ENDPOINT_ACTUATOR_PATTERN
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors(
                cors->cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->
                                req
                                        .requestMatchers(WHITE_LIST_URL).permitAll()
                                        .requestMatchers(POST,ApiConstantEndpoint.API_USER+"/**").hasAnyRole(RoleConstant.PARVULARIA)

                                        .anyRequest().authenticated()



                )
                .sessionManagement(session->session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout->logout.logoutUrl(ApiConstantEndpoint.API_LOGOUT)
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())
                        )
                );
        return httpSecurity.build();
    }
}
