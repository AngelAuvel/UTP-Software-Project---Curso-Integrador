package gm.inventarios.controlador;

import gm.inventarios.modelo.Usuario;
import gm.inventarios.servicio.IUsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("inventario-app") // Base path para todos los endpoints
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioControlador {

    private final IUsuarioServicio usuarioServicio;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioControlador.class);

    // Constructor para Inyección de Dependencias
    public UsuarioControlador(IUsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // ====================================================================
    // ENDPOINT DE AUTENTICACIÓN
    // ====================================================================

    // POST: Logeo de Usuario
    // URL: http://localhost:8080/inventario-app/login
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario credenciales) {
        // Llama al servicio para verificar las credenciales
        Usuario usuario = usuarioServicio.loginUsuario(credenciales.getNombre(), credenciales.getClave());

        if (usuario != null) {
            logger.info("Login exitoso para usuario: " + usuario.getNombre());
            // Devuelve 200 OK con el objeto Usuario si el logeo fue exitoso
            return ResponseEntity.ok(usuario);
        } else {
            logger.warn("Intento de login fallido para nombre: " + credenciales.getNombre());
            // Devuelve 401 Unauthorized si las credenciales son incorrectas
            return ResponseEntity.status(401).build();
        }
    }

    // ====================================================================
    // ENDPOINTS CRUD ESTÁNDAR
    // ====================================================================

    // GET: Listar todos los usuarios
    // URL: http://localhost:8080/inventario-app/usuarios
    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        logger.info("Listado de usuarios solicitado.");
        return usuarioServicio.listarUsuarios();
    }

    // GET: Buscar usuario por ID
    // URL: http://localhost:8080/inventario-app/usuarios/{id}
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioServicio.buscarUsuarioPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    // POST: Crear nuevo usuario
    // URL: http://localhost:8080/inventario-app/usuarios
    @PostMapping("/usuarios")
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        logger.info("Guardando usuario: " + usuario);
        return usuarioServicio.guardarUsuario(usuario);
    }

    // PUT: Actualizar usuario
    // URL: http://localhost:8080/inventario-app/usuarios/{id}
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioRecibido) {
        try {
            Usuario usuarioExistente = usuarioServicio.buscarUsuarioPorId(id);
            if (usuarioExistente == null) {
                return ResponseEntity.notFound().build();
            }

            usuarioRecibido.setId(id);
            Usuario usuarioActualizado = usuarioServicio.guardarUsuario(usuarioRecibido);
            return ResponseEntity.ok(usuarioActualizado);

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar usuario
    // URL: http://localhost:8080/inventario-app/usuarios/{id}
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        try {
            usuarioServicio.eliminarUsuarioPorId(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}