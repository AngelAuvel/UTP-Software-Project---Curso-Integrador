package gm.inventarios.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaSolicitud;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaAprobacion;

    @Transient
    @JsonProperty("id_area")
    private Integer idArea;

    @Transient
    @JsonProperty("id_estado")
    private Integer idEstado;

    @Transient
    @JsonProperty("id_usuario")
    private Integer idUsuario;

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

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    // Getters para los campos Transient (necesarios para el Servicio)
    public Integer getIdArea() {
        return idArea;
    }

    // Setters para los campos Transient (necesarios para la deserialización)
    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", idArea_JSON=" + idArea +
                ", idEstado_JSON=" + idEstado +
                ", idUsuario_JSON=" + idUsuario +
                ", fechaSolicitud=" + fechaSolicitud +
                ", fechaAprobacion=" + fechaAprobacion +
                '}';
    }

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY // <-- Usamos LAZY (o simplemente omitimos el 'fetch')
    )
    @JsonManagedReference
    private List<PedidoDetalle> items = new ArrayList<>();

}