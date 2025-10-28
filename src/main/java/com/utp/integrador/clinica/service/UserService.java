package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.dto.UpdateUsuarioDto;
import com.utp.integrador.clinica.dto.UsuarioDto;
import com.utp.integrador.clinica.model.entities.Usuario;
import com.utp.integrador.clinica.model.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioDto> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UsuarioDto findById(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    public void update(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        // Lógica de actualización
        usuario.setNombres(usuarioDto.nombres());
        usuario.setApellidos(usuarioDto.apellidos());
        usuario.setDni(usuarioDto.dni());
        usuario.setTelefono(usuarioDto.telefono());
        usuario.setDireccion(usuarioDto.direccion());
        usuario.setRol(usuarioDto.rol());
        usuario.setArea(usuarioDto.area());
        usuario.setEstado(usuarioDto.estado());
        usuarioRepository.save(usuario);
    }
    
    public void update(Long id, UpdateUsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        // Lógica de actualización
        usuario.setTelefono(usuarioDto.telefono());
        usuario.setDireccion(usuarioDto.direccion());
        usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    public void changePassword(String userEmail, String newPassword) {
        Usuario usuario = usuarioRepository.findByCorreo(userEmail).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);
    }

    private UsuarioDto convertToDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getIdUsuario(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getDni(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getFechaRegistro(),
                usuario.getUltimoAcceso(),
                usuario.isDebeCambiarContraseña(),
                usuario.getRol(),
                usuario.getArea(),
                usuario.getEstado()
        );
    }
}