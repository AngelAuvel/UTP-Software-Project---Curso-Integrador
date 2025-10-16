package gm.inventarios.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_area", referencedColumnName = "id")
    private Area area;

    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario; // Quien realiza la compra/solicitud

    private LocalDateTime fechaAprobacion;

    private LocalDateTime fechaSolicitud;

    // --- Getters, Setters, Constructores y toString ---
    public Compra() {}

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
        return "Compra{" +
                "id=" + id +
                ", fechaSolicitud=" + fechaSolicitud +
                '}';
    }
}