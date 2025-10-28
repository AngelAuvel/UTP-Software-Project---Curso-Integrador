package com.utp.integrador.clinica.dto;

import com.utp.integrador.clinica.model.entities.Rol;
import java.time.LocalDateTime;

public record UsuarioDto(
    Long idUsuario,
    String nombres,
    String apellidos,
    String dni,
    String correo,
    String telefono,
    String direccion,
    LocalDateTime fechaRegistro,
    LocalDateTime ultimoAcceso,
    boolean debeCambiarContraseña,
    Rol rol,
    String area,
    String estado
) {
}
