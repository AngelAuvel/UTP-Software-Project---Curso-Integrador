package gm.inventarios.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Articulo")
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 45)
    private String descripcion;

    // --- Getters, Setters, Constructores y toString ---
    public Articulo() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}