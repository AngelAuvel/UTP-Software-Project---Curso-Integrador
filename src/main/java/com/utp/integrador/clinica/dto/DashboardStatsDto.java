package com.utp.integrador.clinica.dto;

import java.math.BigDecimal;

public record DashboardStatsDto(
    long totalUsuarios,
    long totalProductos,
    long totalProveedores,
    long solicitudesPendientes,
    long productosStockBajo,
    long movimientosMes,
    BigDecimal valorInventario
) {
}
