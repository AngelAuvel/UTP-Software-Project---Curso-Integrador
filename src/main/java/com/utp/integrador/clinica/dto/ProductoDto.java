package com.utp.integrador.clinica.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductoDto(
    Long id,
    String nombre,
    Long categoriaId,
    String categoriaNombre,
    int cantidadStock,
    int stockMinimo,
    BigDecimal precio,
    BigDecimal precioTotal,
    String codigo,
    Long proveedorId,
    String proveedorNombre,
    String descripcion,
    String estado,
    LocalDateTime fechaRegistro
) {
}
