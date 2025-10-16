package gm.inventarios.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Pedido_Detalle")
public class PedidoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Clave Foránea a Pedido
    @ManyToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id")
    private Pedido pedido;

    // Clave Foránea a Articulo
    @ManyToOne
    @JoinColumn(name = "id_articulo", referencedColumnName = "id")
    private Articulo articulo;

    // CORRECCIÓN: cantidad debe ser un número (INT/Double), no DATETIME
    private Integer cantidad;

    @Column(length = 100)
    private String glosa;

    // --- Getters, Setters, Constructores y toString ---
    public PedidoDetalle() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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
        return "PedidoDetalle{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                '}';
    }
}