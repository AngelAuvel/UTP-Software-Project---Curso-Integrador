package com.utp.integrador.clinica.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utp.integrador.clinica.model.Usuario;
import com.utp.integrador.clinica.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class ForcePasswordChangeFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ForcePasswordChangeFilter.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        logger.info("ForcePasswordChangeFilter: Procesando URI: {}", requestURI);

        if ("/auth/signin".equals(requestURI)) {
            logger.info("Omitiendo filtro para /auth/signin");
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            String userEmail = authentication.getName();
            logger.info("Usuario autenticado: {}", userEmail);

            Usuario usuario = usuarioRepository.findByEmail(userEmail).orElse(null);

            if (usuario != null && usuario.getUltimoAcceso() == null) {
                logger.warn("Usuario {} necesita cambiar su contraseña. ultimo_acceso es NULL.", userEmail);

                if (!"/usuarios/cambiar-clave".equals(requestURI)) {
                    logger.error("Acceso denegado a {}. Forzando cambio de contraseña.", requestURI);
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json");
                    response.getWriter().write(objectMapper.writeValueAsString(
                        Map.of("error", "FORCE_PASSWORD_CHANGE", "message", "El usuario debe cambiar su contraseña.")
                    ));
                    return;
                }
            } else if (usuario != null) {
                logger.info("Usuario {} ya ha cambiado su contraseña. ultimo_acceso: {}", userEmail, usuario.getUltimoAcceso());
            }
        }

        filterChain.doFilter(request, response);
    }
}
