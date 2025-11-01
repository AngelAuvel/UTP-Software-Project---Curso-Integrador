package com.utp.integrador.clinica.dto.request;

import com.utp.integrador.clinica.model.EstadoSolicitud;
import com.utp.integrador.clinica.model.OrigenSolicitud;
import com.utp.integrador.clinica.model.PrioridadSolicitud;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class SolicitudRequest {

    @NotNull(message = "La fecha requerida no puede ser nula")
    private LocalDate fechaRequerida;

    private PrioridadSolicitud prioridad = PrioridadSolicitud.NORMAL;

    private String justificacion;

    private BigDecimal costoTotalEstimado;

    private OrigenSolicitud origen = OrigenSolicitud.MANUAL;

    @Valid
    @Size(min = 1, message = "La solicitud debe contener al menos un producto")
    private List<DetalleSolicitudRequest> detalles;
}
