package com.utp.integrador.clinica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proveedor_id")
    private Integer id;

    @Column(length = 200, nullable = false)
    private String nombre;

    @Column(length = 11, nullable = false, unique = true)
    private String ruc;

    @Column(columnDefinition = "TEXT")
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(length = 200)
    private String email;

    @Column(name = "contacto_nombre", length = 100)
    private String contactoNombre;

    @Column(name = "contacto_telefono", length = 20)
    private String contactoTelefono;

    @Column(name = "condiciones_pago", columnDefinition = "TEXT")
    private String condicionesPago;

    @Column(precision = 3, scale = 2)
    private BigDecimal calificacion;

    @Column(length = 10, nullable = false)
    private String estado = "Activo";
}
