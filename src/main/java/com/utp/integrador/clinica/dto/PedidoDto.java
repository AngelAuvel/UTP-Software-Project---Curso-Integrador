package com.utp.integrador.clinica.dto;

import com.utp.integrador.clinica.model.entities.EstadoPedido;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDto(
    Long id,
    Long solicitadoPorId,
    String solicitadoPorNombre,
    String motivo,
    EstadoPedido estado,
    LocalDateTime fechaSolicitud,
    LocalDateTime fechaRespuesta,
    Long respondidoPorId,
    String respondidoPorNombre,
    List<PedidoDetalleDto> detalles
) {
}
