package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.dto.AuthResponse;
import com.utp.integrador.clinica.dto.LoginRequest;
import com.utp.integrador.clinica.dto.RegisterRequest;
import com.utp.integrador.clinica.model.entities.Usuario;
import com.utp.integrador.clinica.model.repository.UsuarioRepository;
import java.time.LocalDateTime;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        Usuario user = usuarioRepository.findByCorreo(request.username()).orElseThrow();
        user.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.isDebeCambiarContraseña());
    }

    public AuthResponse register(RegisterRequest request) {
        Usuario user = new Usuario();
        user.setNombres(request.nombres());
        user.setApellidos(request.apellidos());
        user.setDni(request.dni());
        user.setCorreo(request.correo());
        user.setTelefono(request.telefono());
        user.setDireccion(request.direccion());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRol(request.rol());
        user.setArea(request.area());
        user.setFechaRegistro(LocalDateTime.now());
        user.setUltimoAcceso(LocalDateTime.now());
        user.setDebeCambiarContraseña(true);
        user.setEstado("ACTIVO");

        usuarioRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.isDebeCambiarContraseña());
    }

    public void changeInitialPassword(String userEmail, String newPassword) {
        Usuario usuario = usuarioRepository.findByCorreo(userEmail).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuario.setDebeCambiarContraseña(false);
        usuarioRepository.save(usuario);
    }
}
