package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

    Page<Usuario> getAllUsuarios(Pageable pageable, String query);

    List<Usuario> getAllUsuariosSinPaginar();

    Usuario getUsuarioById(Integer id);

    Usuario getUsuarioByEmail(String email);

    Usuario saveUsuario(Usuario usuario);

    void deleteUsuario(Integer id);

    boolean cambiarClave(String email, String claveActual, String nuevaClave);
}
