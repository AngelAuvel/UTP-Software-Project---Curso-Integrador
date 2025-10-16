package gm.inventarios.servicio;

import gm.inventarios.modelo.Usuario;
import gm.inventarios.repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UsuarioServicio implements IUsuarioServicio {

    // Inyección de dependencia del repositorio
    private final UsuarioRepositorio usuarioRepositorio;

    // Constructor para inyección
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        // Usamos orElse(null) para devolver null si no se encuentra (siguiendo tu patrón)
        return usuarioRepositorio.findById(idUsuario).orElse(null);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminarUsuarioPorId(Integer idUsuario) {
        if (!usuarioRepositorio.existsById(idUsuario)) {
            throw new NoSuchElementException("No existe el usuario con ID: " + idUsuario);
        }
        usuarioRepositorio.deleteById(idUsuario);
    }

    @Override
    public Usuario loginUsuario(String nombre, String clave) {
        // Usa el método del repositorio para buscar la coincidencia
        Usuario usuario = usuarioRepositorio.findByNombreAndClave(nombre, clave);

        if (usuario == null) {
            // Puedes lanzar una excepción si la autenticación falla
            // throw new AutenticacionFallidaException("Credenciales inválidas");
            return null;
        }
        return usuario;
    }
}