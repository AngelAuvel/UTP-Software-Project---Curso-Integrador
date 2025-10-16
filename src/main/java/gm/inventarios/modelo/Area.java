package gm.inventarios.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Area")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // CORRECCIÓN: nombre debe ser String, no INT
    @Column(length = 100)
    private String nombre;

    // --- Getters, Setters, Constructores y toString ---
    public Area() {}

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

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}