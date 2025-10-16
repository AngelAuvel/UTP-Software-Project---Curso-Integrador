package gm.inventarios.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10) // varchar(10)
    private String nombre;

    @Column(length = 250) // varchar(250)
    private String clave;

    // Relación OneToMany (Opcional: si quieres navegar desde Usuario)
    // @OneToMany(mappedBy = "usuario")
    // private Set<Pedido> pedidos;

    // --- Getters, Setters, Constructores y toString ---
    public Usuario() {}

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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}