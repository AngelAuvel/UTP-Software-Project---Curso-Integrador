package gm.inventarios.servicio;

import gm.inventarios.modelo.Usuario;
import java.util.List;

public interface IUsuarioServicio {

    List<Usuario> listarUsuarios();

    Usuario buscarUsuarioPorId(Integer idUsuario);

    Usuario guardarUsuario(Usuario usuario);

    void eliminarUsuarioPorId(Integer idUsuario);

    Usuario loginUsuario(String nombre, String clave);
}