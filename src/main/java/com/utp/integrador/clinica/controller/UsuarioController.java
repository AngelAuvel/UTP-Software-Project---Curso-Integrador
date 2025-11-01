package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.dto.CambiarClaveRequest;
import com.utp.integrador.clinica.model.Usuario;
import com.utp.integrador.clinica.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Importar Pageable
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN_LOGISTICA')")
    public Page<Usuario> getAllUsuarios(Pageable pageable, @RequestParam(required = false) String query) {
        return usuarioService.getAllUsuarios(pageable, query);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN_LOGISTICA')") // Proteger este endpoint también
    public List<Usuario> getAllUsuariosSinPaginar() {
        return usuarioService.getAllUsuariosSinPaginar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_LOGISTICA')")
    public Usuario getUsuarioById(@PathVariable Integer id) {
        return usuarioService.getUsuarioById(id);
    }

    @GetMapping("/perfil")
    @PreAuthorize("isAuthenticated()") // Solo usuarios autenticados pueden ver su perfil
    public Usuario getPerfilUsuario(Authentication authentication) {
        String userEmail = authentication.getName();
        return usuarioService.getUsuarioByEmail(userEmail);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN_LOGISTICA')")
    public Usuario saveUsuario(@RequestBody Usuario usuario) {
        return usuarioService.saveUsuario(usuario);
    }

    @PutMapping("/{id}") // Cambiado a PUT para actualizar
    @PreAuthorize("hasRole('ADMIN_LOGISTICA')")
    public Usuario updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        // Asegurarse de que el ID del path coincida con el del body si se envía
        usuario.setId(id);
        return usuarioService.saveUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_LOGISTICA')")
    public void deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
    }

    @PostMapping("/cambiar-clave")
    @PreAuthorize("isAuthenticated()") // Solo usuarios autenticados pueden cambiar su clave
    public ResponseEntity<?> cambiarClave(@RequestBody CambiarClaveRequest request, Authentication authentication) {
        String userEmail = authentication.getName();
        boolean success = usuarioService.cambiarClave(userEmail, request.getClaveActual(), request.getNuevaClave());

        if (success) {
            logger.info("cambiarClave endpoint: Retornando 200 OK.");
            return ResponseEntity.ok().body(Map.of("message", "Contraseña cambiada exitosamente."));
        }

        logger.warn("cambiarClave endpoint: Retornando 400 Bad Request. Clave actual incorrecta.");
        return ResponseEntity.badRequest().body(Map.of("error", "La contraseña actual es incorrecta."));
    }
}
