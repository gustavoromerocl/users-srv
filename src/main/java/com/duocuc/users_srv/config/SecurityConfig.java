package com.duocuc.users_srv.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    logger.info("Configuring security filter chain for API REST...");

    http
        .csrf(AbstractHttpConfigurer::disable) // Desactivar CSRF, ya que es una API REST
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/api/auth/**").permitAll() // Permitir acceso público a las rutas de autenticación
            .requestMatchers("/api/admin/**").hasRole("ADMIN") // Rutas protegidas para usuarios con rol ADMIN
            .anyRequest().authenticated() // Requerir autenticación para el resto de las rutas
        )
        .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Configurar
                                                                                                         // el manejo de
                                                                                                         // sesión sin
                                                                                                         // estado
                                                                                                         // (stateless)

    logger.info("Security filter chain configured successfully for API REST.");
    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
