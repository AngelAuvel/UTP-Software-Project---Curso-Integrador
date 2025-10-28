package com.utp.integrador.clinica.dto;

import com.utp.integrador.clinica.model.entities.Rol;

public record RegisterRequest(
    String nombres,
    String apellidos,
    String dni,
    String correo,
    String telefono,
    String direccion,
    String password,
    Rol rol,
    String area
) {
}
