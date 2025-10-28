package com.utp.integrador.clinica.dto;

public record ProveedorDto(
    Long id,
    String nombre,
    String ruc,
    String telefono,
    String direccion,
    String estado
) {
}
