package gm.inventarios.controlador;

import gm.inventarios.modelo.PedidoDetalle;
import gm.inventarios.servicio.IPedidoDetalleServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventario-app") // Base path para todos los endpoints
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoDetalleControlador {

    private final IPedidoDetalleServicio pedidoDetalleServicio;
    private static final Logger logger = LoggerFactory.getLogger(PedidoDetalleControlador.class);

    // Constructor para Inyección de Dependencias
    public PedidoDetalleControlador(IPedidoDetalleServicio pedidoDetalleServicio) {
        this.pedidoDetalleServicio = pedidoDetalleServicio;
    }

    // GET: Listar todos los detalles de pedidos
    // URL: http://localhost:8080/inventario-app/pedidos/detalles
    @GetMapping("/pedidos/detalles")
    public List<PedidoDetalle> listarDetalles() {
        logger.info("Listado de detalles de pedido solicitado.");
        return pedidoDetalleServicio.listarDetallesPedido();
    }

    // GET: Buscar detalle de pedido por ID
    // URL: http://localhost:8080/inventario-app/pedidos/detalles/{id}
    @GetMapping("/pedidos/detalles/{id}")
    public ResponseEntity<PedidoDetalle> buscarDetallePorId(@PathVariable Integer id) {
        PedidoDetalle detalle = pedidoDetalleServicio.buscarDetallePedidoPorId(id);
        if (detalle == null) {
            // Devuelve 404 Not Found si no se encuentra
            return ResponseEntity.notFound().build();
        }
        // Devuelve 200 OK con el objeto PedidoDetalle
        return ResponseEntity.ok(detalle);
    }

    // NOTA: POST, PUT, y DELETE generalmente se manejan a través del servicio principal de 'Pedido',
    // ya que los detalles están intrínsecamente ligados a su cabecera.
}