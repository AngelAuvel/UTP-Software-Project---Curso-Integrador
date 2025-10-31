package gm.inventarios.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object para recibir el Pedido desde el frontend de Angular.
 * Usa @JsonProperty para mapear los nombres snake_case del JSON.
 */
public class PedidoDTO {
    private Integer id;

    // Fechas
    private LocalDate fechaSolicitud; // ¡Debe ser LocalDate!
    private LocalDate fechaAprobacion;

    // Claves Foráneas: Usamos @JsonProperty para mapear el snake_case de Angular
    @JsonProperty("id_area")
    private Integer idArea;

    @JsonProperty("id_estado")
    private Integer idEstado;

    @JsonProperty("id_usuario")
    private Integer idUsuario;

    // Lista de detalles (debe coincidir con 'items' del modelo Angular)
    private List<PedidoDetalleDTO> items;

    // --- Getters y Setters ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public LocalDate getFechaAprobacion() { return fechaAprobacion; }
    public void setFechaAprobacion(LocalDate fechaAprobacion) { this.fechaAprobacion = fechaAprobacion; }

    public Integer getIdArea() { return idArea; }
    public void setIdArea(Integer idArea) { this.idArea = idArea; }

    public Integer getIdEstado() { return idEstado; }
    public void setIdEstado(Integer idEstado) { this.idEstado = idEstado; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public List<PedidoDetalleDTO> getItems() { return items; }
    public void setItems(List<PedidoDetalleDTO> items) { this.items = items; }
}
