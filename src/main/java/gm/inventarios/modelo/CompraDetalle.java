package gm.inventarios.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Compra_Detalle")
public class CompraDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_compra", referencedColumnName = "id")
    private Compra compra;

    // Clave Foránea a Articulo
    @ManyToOne
    @JoinColumn(name = "id_articulo", referencedColumnName = "id")
    private Articulo articulo;

    // CORRECCIÓN: cantidad debe ser un número (INT/Double), no DATETIME
    private Integer cantidad;

    @Column(length = 100)
    private String glosa;

    // --- Getters, Setters, Constructores y toString ---
    public CompraDetalle() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    @Override
    public String toString() {
        return "CompraDetalle{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                '}';
    }
}