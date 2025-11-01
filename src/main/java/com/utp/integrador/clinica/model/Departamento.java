package com.utp.integrador.clinica.model;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "departamentos")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departamento_id")
    private Integer id;

    @Column(length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(length = 20, nullable = false, unique = true)
    private String codigo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "presupuesto_mensual", precision = 10, scale = 2)
    private BigDecimal presupuestoMensual;

    @Column(name = "jefe_departamento_id")
    private Integer jefeDepartamentoId;

    @Column(length = 10, nullable = false)
    private String estado = "Activo";
}
