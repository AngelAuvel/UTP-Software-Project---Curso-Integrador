package com.utp.integrador.clinica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solicitud_id")
    private Integer id;

    @Column(name = "numero_solicitud", length = 50, nullable = false, unique = true)
    private String numeroSolicitud;

    @ManyToOne
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "usuario_solicitante_id", nullable = false)
    private Usuario solicitante;

    @Column(name = "fecha_solicitud", nullable = false, updatable = false)
    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    @Column(name = "fecha_requerida", nullable = false)
    private LocalDate fechaRequerida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadSolicitud prioridad = PrioridadSolicitud.NORMAL;

    @Column(columnDefinition = "TEXT")
    private String justificacion;

    @ManyToOne
    @JoinColumn(name = "usuario_aprobador_id")
    private Usuario aprobador;

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    @Column(name = "motivo_rechazo", columnDefinition = "TEXT")
    private String motivoRechazo;

    @ManyToOne
    @JoinColumn(name = "usuario_despachador_id")
    private Usuario despachador;

    @Column(name = "fecha_despacho")
    private LocalDateTime fechaDespacho;

    @ManyToOne
    @JoinColumn(name = "usuario_receptor_id")
    private Usuario receptor;

    @Column(name = "fecha_recepcion")
    private LocalDateTime fechaRecepcion;

    @Column(name = "observaciones_recepcion", columnDefinition = "TEXT")
    private String observacionesRecepcion;

    @Column(name = "costo_total_estimado", precision = 10, scale = 2)
    private BigDecimal costoTotalEstimado;

    @Enumerated(EnumType.STRING)
    @Column(name = "origen_solicitud", nullable = false)
    private OrigenSolicitud origen = OrigenSolicitud.MANUAL;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleSolicitud> detalles;
}
