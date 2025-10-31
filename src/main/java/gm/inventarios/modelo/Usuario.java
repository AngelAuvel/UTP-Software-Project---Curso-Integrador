package gm.inventarios.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // <--- CRUCIAL: Añade @Data si no lo tienes.
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // <--- CRUCIAL: Debe ser Integer, no int.

    private String nombre;
    private String clave;
    // ... otros campos

    // Si no usas @Data de Lombok, debes tener un getter:
    // public Integer getId() { return id; }
}
