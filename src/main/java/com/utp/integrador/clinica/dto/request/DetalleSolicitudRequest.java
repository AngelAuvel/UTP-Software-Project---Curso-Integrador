package com.utp.integrador.clinica.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DetalleSolicitudRequest {

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Integer productoId;

    @Min(value = 1, message = "La cantidad solicitada debe ser al menos 1")
    private Integer cantidadSolicitada;

    private String especificaciones;
}
