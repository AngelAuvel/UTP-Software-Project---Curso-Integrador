package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.dto.ChangePasswordRequest;
import com.utp.integrador.clinica.dto.UpdateUsuarioDto;
import com.utp.integrador.clinica.dto.UsuarioDto;
import com.utp.integrador.clinica.model.entities.Usuario;
import com.utp.integrador.clinica.service.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UsuarioDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto) {
        userService.update(id, usuarioDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/me")
    public ResponseEntity<UsuarioDto> getCurrentUser(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(userService.findById(usuario.getIdUsuario()));
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateCurrentUser(@AuthenticationPrincipal Usuario usuario, @RequestBody UpdateUsuarioDto usuarioDto) {
        userService.update(usuario.getIdUsuario(), usuarioDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/me/change-password")
    public ResponseEntity<Void> changeCurrentUserPassword(Principal principal, @RequestBody ChangePasswordRequest request) {
        userService.changePassword(principal.getName(), request.newPassword());
        return ResponseEntity.ok().build();
    }
}
