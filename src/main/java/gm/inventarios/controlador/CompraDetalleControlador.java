package gm.inventarios.controlador;

import gm.inventarios.modelo.CompraDetalle;
import gm.inventarios.servicio.ICompraDetalleServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventario-app") // Base path para todos los endpoints
@CrossOrigin(origins = "http://localhost:4200")
public class CompraDetalleControlador {

    private final ICompraDetalleServicio compraDetalleServicio;
    private static final Logger logger = LoggerFactory.getLogger(CompraDetalleControlador.class);

    // Constructor para Inyección de Dependencias
    public CompraDetalleControlador(ICompraDetalleServicio compraDetalleServicio) {
        this.compraDetalleServicio = compraDetalleServicio;
    }

    // GET: Listar todos los detalles de compras
    // URL: http://localhost:8080/inventario-app/compras/detalles
    @GetMapping("/compras/detalles")
    public List<CompraDetalle> listarDetalles() {
        logger.info("Listado de detalles de compra solicitado.");
        return compraDetalleServicio.listarDetallesCompra();
    }

    // GET: Buscar detalle de compra por ID
    // URL: http://localhost:8080/inventario-app/compras/detalles/{id}
    @GetMapping("/compras/detalles/{id}")
    public ResponseEntity<CompraDetalle> buscarDetallePorId(@PathVariable Integer id) {
        CompraDetalle detalle = compraDetalleServicio.buscarDetalleCompraPorId(id);
        if (detalle == null) {
            // Devuelve 404 Not Found si no se encuentra
            return ResponseEntity.notFound().build();
        }
        // Devuelve 200 OK con el objeto CompraDetalle
        return ResponseEntity.ok(detalle);
    }

    // NOTA: POST, PUT, y DELETE generalmente se omiten para tablas de detalle,
    // ya que su gestión se realiza a través del servicio principal de la entidad 'Compra'.
}