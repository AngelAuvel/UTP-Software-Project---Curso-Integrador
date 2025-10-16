package gm.inventarios.controlador;

import gm.inventarios.modelo.Compra;
import gm.inventarios.servicio.ICompraServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("inventario-app") // Base path para todos los endpoints
@CrossOrigin(origins = "http://localhost:4200")
public class CompraControlador {

    private final ICompraServicio compraServicio;
    private static final Logger logger = LoggerFactory.getLogger(CompraControlador.class);

    // Constructor para Inyección de Dependencias
    public CompraControlador(ICompraServicio compraServicio) {
        this.compraServicio = compraServicio;
    }

    // GET: Listar todas las compras
    // URL: http://localhost:8080/inventario-app/compras
    @GetMapping("/compras")
    public List<Compra> listarCompras() {
        logger.info("Listado de compras solicitado.");
        return compraServicio.listarCompras();
    }

    // GET: Buscar compra por ID
    // URL: http://localhost:8080/inventario-app/compras/{id}
    @GetMapping("/compras/{id}")
    public ResponseEntity<Compra> buscarCompraPorId(@PathVariable Integer id) {
        Compra compra = compraServicio.buscarCompraPorId(id);
        if (compra == null) {
            // Devuelve 404 Not Found si no se encuentra
            return ResponseEntity.notFound().build();
        }
        // Devuelve 200 OK con el objeto Compra
        return ResponseEntity.ok(compra);
    }

    // POST: Guardar (Crear) una nueva compra
    // URL: http://localhost:8080/inventario-app/compras
    @PostMapping("/compras")
    public Compra guardarCompra(@RequestBody Compra compra) {
        logger.info("Guardando compra: " + compra);
        return compraServicio.guardarCompra(compra);
    }

    // PUT: Actualizar una compra existente
    // URL: http://localhost:8080/inventario-app/compras/{id}
    @PutMapping("/compras/{id}")
    public ResponseEntity<Compra> actualizarCompra(@PathVariable Integer id, @RequestBody Compra compraRecibida) {
        try {
            // 1. Verifica que la compra exista
            Compra compraExistente = compraServicio.buscarCompraPorId(id);
            if (compraExistente == null) {
                return ResponseEntity.notFound().build();
            }

            // 2. Establece el ID de la ruta en el objeto recibido
            compraRecibida.setId(id);

            // 3. Guarda (Actualiza) los cambios
            Compra compraActualizada = compraServicio.guardarCompra(compraRecibida);
            return ResponseEntity.ok(compraActualizada);

        } catch (NoSuchElementException e) {
            // Captura si el servicio lanza la excepción
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar una compra por ID
    // URL: http://localhost:8080/inventario-app/compras/{id}
    @DeleteMapping("/compras/{id}")
    public ResponseEntity<Void> eliminarCompra(@PathVariable Integer id) {
        try {
            compraServicio.eliminarCompraPorId(id);
            // Devuelve 204 No Content para indicar eliminación exitosa
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            // Devuelve 404 Not Found si no existe la compra
            return ResponseEntity.notFound().build();
        }
    }
}