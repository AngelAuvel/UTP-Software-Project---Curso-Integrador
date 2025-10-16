package gm.inventarios.controlador;

import gm.inventarios.modelo.Articulo;
import gm.inventarios.servicio.IArticuloServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("inventario-app") // Base path para todos los endpoints
@CrossOrigin(origins = "http://localhost:4200")
public class ArticuloControlador {

    private final IArticuloServicio articuloServicio;
    private static final Logger logger = LoggerFactory.getLogger(ArticuloControlador.class);

    // Constructor para Inyección de Dependencias
    public ArticuloControlador(IArticuloServicio articuloServicio) {
        this.articuloServicio = articuloServicio;
    }

    // GET: Listar todos los artículos
    // URL: http://localhost:8080/inventario-app/articulos
    @GetMapping("/articulos")
    public List<Articulo> listarArticulos() {
        logger.info("Listado de artículos solicitado.");
        return articuloServicio.listarArticulos();
    }

    // GET: Buscar artículo por ID
    // URL: http://localhost:8080/inventario-app/articulos/{id}
    @GetMapping("/articulos/{id}")
    public ResponseEntity<Articulo> buscarArticuloPorId(@PathVariable Integer id) {
        Articulo articulo = articuloServicio.buscarArticuloPorId(id);
        if (articulo == null) {
            // Devuelve 404 Not Found si no se encuentra
            return ResponseEntity.notFound().build();
        }
        // Devuelve 200 OK con el objeto Articulo
        return ResponseEntity.ok(articulo);
    }

    // POST: Guardar (Crear) un nuevo artículo
    // URL: http://localhost:8080/inventario-app/articulos
    @PostMapping("/articulos")
    public Articulo guardarArticulo(@RequestBody Articulo articulo) {
        logger.info("Guardando artículo: " + articulo);
        return articuloServicio.guardarArticulo(articulo);
    }

    // PUT: Actualizar un artículo existente
    // URL: http://localhost:8080/inventario-app/articulos/{id}
    @PutMapping("/articulos/{id}")
    public ResponseEntity<Articulo> actualizarArticulo(@PathVariable Integer id, @RequestBody Articulo articuloRecibido) {
        try {
            // 1. Verifica que el artículo exista
            Articulo articuloExistente = articuloServicio.buscarArticuloPorId(id);
            if (articuloExistente == null) {
                return ResponseEntity.notFound().build();
            }

            // 2. Establece el ID de la ruta en el objeto recibido
            articuloRecibido.setId(id);

            // 3. Guarda (Actualiza) los cambios
            Articulo articuloActualizado = articuloServicio.guardarArticulo(articuloRecibido);
            return ResponseEntity.ok(articuloActualizado);

        } catch (NoSuchElementException e) {
            // Captura si el servicio lanza la excepción
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar un artículo por ID
    // URL: http://localhost:8080/inventario-app/articulos/{id}
    @DeleteMapping("/articulos/{id}")
    public ResponseEntity<Void> eliminarArticulo(@PathVariable Integer id) {
        try {
            articuloServicio.eliminarArticuloPorId(id);
            // Devuelve 204 No Content para indicar eliminación exitosa
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            // Devuelve 404 Not Found si no existe el artículo
            return ResponseEntity.notFound().build();
        }
    }
}