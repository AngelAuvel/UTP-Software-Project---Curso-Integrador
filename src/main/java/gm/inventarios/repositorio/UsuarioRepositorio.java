package gm.inventarios.repositorio;

import gm.inventarios.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Usuario findByNombreAndClave(String nombre, String clave);
}