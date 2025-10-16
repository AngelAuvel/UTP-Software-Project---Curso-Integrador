package gm.inventarios.controlador;

import gm.inventarios.modelo.Estado;
import gm.inventarios.servicio.IEstadoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventario-app") // Base path para todos los endpoints
@CrossOrigin(origins = "http://localhost:4200")
public class EstadoControlador {

    private final IEstadoServicio estadoServicio;
    private static final Logger logger = LoggerFactory.getLogger(EstadoControlador.class);

    // Constructor para Inyección de Dependencias
    public EstadoControlador(IEstadoServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    // GET: Listar todos los estados
    // URL: http://localhost:8080/inventario-app/estados
    @GetMapping("/estados")
    public List<Estado> listarEstados() {
        logger.info("Listado de estados (catálogo) solicitado.");
        return estadoServicio.listarEstados();
    }

    // GET: Buscar estado por ID
    // URL: http://localhost:8080/inventario-app/estados/{id}
    @GetMapping("/estados/{id}")
    public ResponseEntity<Estado> buscarEstadoPorId(@PathVariable Integer id) {
        Estado estado = estadoServicio.buscarEstadoPorId(id);
        if (estado == null) {
            // Devuelve 404 Not Found si no se encuentra
            return ResponseEntity.notFound().build();
        }
        // Devuelve 200 OK con el objeto Estado
        return ResponseEntity.ok(estado);
    }

    // NOTA: Se omiten POST, PUT y DELETE ya que los datos de catálogo rara vez
    // se modifican a través de una API REST pública.
}