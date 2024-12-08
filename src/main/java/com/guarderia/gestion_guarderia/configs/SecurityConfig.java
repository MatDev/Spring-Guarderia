package com.guarderia.gestion_guarderia.configs;

import com.guarderia.gestion_guarderia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;
    /*
        Propósito:
        Manejar el cifrado de contraseñas.
        Función:
        Utiliza el algoritmo BCrypt para encriptar contraseñas.
        Es seguro, ya que incluye un "salt" (valor aleatorio) para prevenir ataques de rainbow tables.
        Uso:
        Se utiliza para cifrar contraseñas al registrarlas y para compararlas al autenticar.

     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
        Propósito:
        Proporciona una forma de cargar los detalles de un usuario desde la base de datos para autenticación.
        Función:
        Implementa la interfaz UserDetailsService, utilizada por Spring Security para encontrar un usuario por su nombre de usuario (en este caso, el correo electrónico).
        Si el usuario no es encontrado, lanza una excepción UsernameNotFoundException.
        Uso:
        Spring Security llama automáticamente a este bean cuando necesita autenticar a un usuario.
     */
    @Bean
    public UserDetailsService userDetailsService(){
       return username -> usuarioRepository.findByRut(username)
               .orElseThrow(() -> new IllegalStateException("Usuario con rut " + username + " no encontrado"));
    }

    /*
        Propósito:
        Configurar el proveedor de autenticación que Spring Security utilizará para autenticar usuarios.
        Función:
        Usa DaoAuthenticationProvider, que autentica usuarios mediante un UserDetailsService y un PasswordEncoder.
        setUserDetailsService: Asocia el UserDetailsService definido anteriormente.
        setPasswordEncoder: Configura el PasswordEncoder para comparar contraseñas.
        Uso:
        Spring Security lo utiliza internamente para procesar solicitudes de autenticación.
     */

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    /*
        Propósito:
        Gestionar la autenticación en la aplicación.
        Función:
        Recupera el AuthenticationManager configurado por Spring Security.
        Es el punto central que verifica las credenciales del usuario contra el AuthenticationProvider.
        Uso:
        Se utiliza para autenticar programáticamente en casos como APIs de autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    /*
         Propósito:
        Configurar las políticas de CORS (Cross-Origin Resource Sharing).
        Permitir o restringir solicitudes desde diferentes orígenes.
        Función:
        setAllowedOriginPatterns: Permite solicitudes desde cualquier origen.
        setAllowedMethods: Permite todos los métodos HTTP (GET, POST, PUT, DELETE, etc.).
        setAllowCredentials: Permite enviar cookies o encabezados de autenticación.
        setAllowedHeaders: Permite todos los encabezados.
        setExposedHeaders: Expone encabezados específicos en la respuesta (útil para autenticación).
        setMaxAge: Define el tiempo en segundos que el navegador puede almacenar en caché la configuración CORS.
        Uso:
        Lo utiliza Spring Security para configurar automáticamente las políticas de CORS en las rutas protegidas.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setExposedHeaders(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
