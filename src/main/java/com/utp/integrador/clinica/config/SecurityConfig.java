package com.utp.integrador.clinica.config;

import com.utp.integrador.clinica.security.ForcePasswordChangeFilter;
import com.utp.integrador.clinica.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Importar HttpMethod
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Habilitar seguridad a nivel de método
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ForcePasswordChangeFilter forcePasswordChangeFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/usuarios/cambiar-clave").authenticated()
                // Reglas de autorización para productos
                .requestMatchers(HttpMethod.POST, "/productos").hasRole("ADMIN_LOGISTICA") // Crear producto
                .requestMatchers(HttpMethod.PUT, "/productos/**").hasRole("ADMIN_LOGISTICA") // Editar producto
                .requestMatchers(HttpMethod.DELETE, "/productos/**").hasRole("ADMIN_LOGISTICA") // Eliminar producto
                .requestMatchers(HttpMethod.GET, "/productos/**").authenticated() // Ver productos (cualquier autenticado)
                .requestMatchers(HttpMethod.GET, "/productos").authenticated() // Listar productos (cualquier autenticado)
                .requestMatchers(HttpMethod.GET, "/categorias").authenticated() // Listar categorías (cualquier autenticado)
                
                // NUEVAS REGLAS DE AUTORIZACIÓN PARA SOLICITUDES
                // .requestMatchers(HttpMethod.POST, "/solicitudes").authenticated() // TEMPORALMENTE: Permitir a cualquier autenticado
                .requestMatchers(HttpMethod.POST, "/solicitudes").hasAnyRole("ADMIN_LOGISTICA", "JEFE_DEPARTAMENTO", "EMPLEADO_GENERAL") // Original
                .requestMatchers(HttpMethod.GET, "/solicitudes/**").authenticated() // Ver solicitudes (cualquier autenticado)
                .requestMatchers(HttpMethod.GET, "/solicitudes").authenticated() // Listar solicitudes (cualquier autenticado)
                // Puedes añadir más reglas para PUT/DELETE de solicitudes si es necesario

                // Asegúrate de que las reglas más específicas vayan antes que las más generales
                .anyRequest().authenticated()
            );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(forcePasswordChangeFilter, JwtAuthenticationFilter.class);

        return http.build();
    }
}
