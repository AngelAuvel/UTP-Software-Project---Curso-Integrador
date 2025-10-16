package gm.inventarios.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Estado")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String nombre;

    // --- Getters, Setters, Constructores y toString ---
    public Estado() {}

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
        return "Estado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}