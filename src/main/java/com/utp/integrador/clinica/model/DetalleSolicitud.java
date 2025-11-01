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
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_solicitudes")
public class DetalleSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad_solicitada", nullable = false)
    private Integer cantidadSolicitada;

    @Column(name = "cantidad_aprobada")
    private Integer cantidadAprobada;

    @Column(name = "cantidad_despachada")
    private Integer cantidadDespachada;

    @Column(name = "cantidad_recibida")
    private Integer cantidadRecibida;

    @Column(columnDefinition = "TEXT")
    private String especificaciones;

    @Column(name = "precio_unitario_estimado", precision = 10, scale = 2)
    private BigDecimal precioUnitarioEstimado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_item", nullable = false)
    private EstadoItem estadoItem = EstadoItem.PENDIENTE;
}
