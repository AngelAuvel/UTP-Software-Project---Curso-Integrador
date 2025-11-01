package com.utp.integrador.clinica.service.impl;

import com.utp.integrador.clinica.model.Usuario;
import com.utp.integrador.clinica.repository.UsuarioRepository;
import com.utp.integrador.clinica.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<Usuario> getAllUsuarios(Pageable pageable, String query) {
        if (StringUtils.hasText(query)) {
            return usuarioRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, query, pageable);
        }
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public List<Usuario> getAllUsuariosSinPaginar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    @Transactional
    public Usuario saveUsuario(Usuario usuario) {
        if (usuario.getId() != null) {
            Usuario existingUser = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (existingUser != null && (usuario.getPassword() == null || usuario.getPassword().isEmpty())) {
                usuario.setPassword(existingUser.getPassword());
            } else if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
        } else {
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean cambiarClave(String email, String claveActual, String nuevaClave) {
        logger.info("cambiarClave: Intentando cambiar clave para usuario: {}", email);
        logger.info("cambiarClave: Clave actual recibida del frontend: {}", claveActual);
        logger.info("cambiarClave: Nueva clave recibida del frontend: {}", nuevaClave);

        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) {
            logger.warn("cambiarClave: Usuario {} no encontrado para cambiar clave. Retornando FALSE.", email);
            return false;
        }

        logger.info("cambiarClave: Clave hasheada almacenada en DB: {}", usuario.getPassword());
        boolean matches = passwordEncoder.matches(claveActual, usuario.getPassword());
        logger.info("cambiarClave: Resultado de passwordEncoder.matches(): {}", matches);

        if (matches) {
            // Add check to prevent new password from being the same as the current password
            if (passwordEncoder.matches(nuevaClave, usuario.getPassword())) {
                logger.warn("cambiarClave: La nueva clave no puede ser igual a la clave actual para usuario: {}. Retornando FALSE.", email);
                throw new IllegalArgumentException("La nueva clave no puede ser igual a la clave actual.");
            }

            logger.info("cambiarClave: Clave actual coincide. Procediendo a actualizar.");
            usuario.setPassword(passwordEncoder.encode(nuevaClave));
            usuario.setUltimoAcceso(LocalDateTime.now());
            Usuario savedUsuario = usuarioRepository.save(usuario);
            logger.info("cambiarClave: Usuario guardado en DB. ID: {}, UltimoAcceso: {}", savedUsuario.getId(), savedUsuario.getUltimoAcceso());
            logger.info("cambiarClave: Contraseña actualizada exitosamente para {}. Retornando TRUE.", email);
            return true;
        } else {
            logger.warn("cambiarClave: Clave actual NO coincide para usuario: {}. Retornando FALSE.", email);
            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
        }
    }
}
