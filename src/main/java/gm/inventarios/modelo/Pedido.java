package gm.inventarios.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime; // Usamos LocalDateTime para DATETIME

@Entity
@Table(name = "Pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Clave Foránea a Area
    @ManyToOne
    @JoinColumn(name = "id_area", referencedColumnName = "id")
    private Area area;

    // Clave Foránea a Estado
    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    private Estado estado;

    // Clave Foránea a Usuario (quien solicita)
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    private LocalDateTime fechaAprobacion; // Mapea a fecha_aprobacion DATETIME

    private LocalDateTime fechaSolicitud; // Mapea a fecha_solicitud DATETIME

    // --- Getters, Setters, Constructores y toString ---
    public Pedido() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", fechaSolicitud=" + fechaSolicitud +
                '}';
    }
}