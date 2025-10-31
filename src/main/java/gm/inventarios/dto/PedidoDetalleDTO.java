package gm.inventarios.dto;

// Uso de Lombok recomendado si lo tiene configurado,
// pero usaremos getters/setters explícitos como lo ha hecho.

import com.fasterxml.jackson.annotation.JsonProperty;

public class PedidoDetalleDTO {

    private Integer id;
    @JsonProperty("id_articulo")
    private Integer idArticulo;// Se recibe el ID
    private Integer cantidad;
    private String glosa;

    // Getters y Setters

    // --- ERROR CORREGIDO AQUÍ ---
    public Integer getId() { return id; }
    public void setId(Integer id) {
        this.id = id; // <<-- ¡CORREGIDO! Ahora asigna el parámetro 'id'
    }
    // ----------------------------

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getGlosa() { return glosa; }
    public void setGlosa(String glosa) { this.glosa = glosa; }

    public Integer getIdArticulo() { return idArticulo; }
    public void setIdArticulo(Integer idArticulo) { this.idArticulo = idArticulo; }

}
