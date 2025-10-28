package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.dto.AuthResponse;
import com.utp.integrador.clinica.dto.ChangePasswordRequest;
import com.utp.integrador.clinica.dto.LoginRequest;
import com.utp.integrador.clinica.dto.RegisterRequest;
import com.utp.integrador.clinica.service.AuthService;
import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changeInitialPassword(Principal principal, @RequestBody ChangePasswordRequest request) {
        authService.changeInitialPassword(principal.getName(), request.newPassword());
        return ResponseEntity.ok().build();
    }
}
