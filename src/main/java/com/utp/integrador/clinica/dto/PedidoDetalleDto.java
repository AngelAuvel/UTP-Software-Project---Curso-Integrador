package com.utp.integrador.clinica.dto;

public record PedidoDetalleDto(
    Long id,
    Long productoId,
    String productoNombre,
    int cantidad
) {
}
