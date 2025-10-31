package gm.inventarios.controlador;

import gm.inventarios.dto.PedidoDTO;
import gm.inventarios.modelo.Pedido;
import gm.inventarios.servicio.IPedidoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("inventario-app") // Base path para todos los endpoints
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoControlador {

    private final IPedidoServicio pedidoServicio;
    private static final Logger logger = LoggerFactory.getLogger(PedidoControlador.class);

    // Constructor para Inyección de Dependencias
    public PedidoControlador(IPedidoServicio pedidoServicio) {
        this.pedidoServicio = pedidoServicio;
    }

    // GET: Listar todos los pedidos
    // URL: http://localhost:8080/inventario-app/pedidos
    @GetMapping("/pedidos")
    public List<Pedido> listarPedidos() {
        logger.info("Listado de pedidos solicitado.");
        return pedidoServicio.listarPedidos();
    }

    // GET: Buscar pedido por ID
    // URL: http://localhost:8080/inventario-app/pedidos/{id}
    @GetMapping("/pedidos/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Integer id) {
        Pedido pedido = pedidoServicio.buscarPedidoPorId(id);
        if (pedido == null) {
            // Devuelve 404 Not Found si no se encuentra
            return ResponseEntity.notFound().build();
        }
        // Devuelve 200 OK con el objeto Pedido
        return ResponseEntity.ok(pedido);
    }

    // POST: Guardar (Crear) un nuevo pedido
    // URL: http://localhost:8080/inventario-app/pedidos
    @PostMapping("/pedidos")
    public ResponseEntity<Pedido> guardarPedido(@RequestBody PedidoDTO pedidoDto) {
        logger.info("Pedido DTO a guardar: " + pedidoDto);

        // CORRECCIÓN: Llamamos directamente al método del servicio que recibe el DTO
        // y devuelve la entidad guardada, eliminando la llamada a 'convertirDtoAEntidad'.
        try {
            Pedido nuevoPedido = pedidoServicio.guardarPedido(pedidoDto);
            return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error al guardar el pedido (Datos Inválidos): " + e.getMessage());
            // Devuelve un error 400 Bad Request si faltan IDs, etc.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error interno al guardar el pedido: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT: Actualizar un pedido existente
    // URL: http://localhost:8080/inventario-app/pedidos/{id}
    @PutMapping("/pedidos/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Integer id, @RequestBody PedidoDTO pedidoDto) {
        logger.info("Pedido DTO a actualizar (ID: " + id + "): " + pedidoDto);

        // 1. Asignar el ID de la ruta al DTO para que el servicio lo reconozca como actualización
        pedidoDto.setId(id);

        try {
            // 2. Reutilizar el método guardarPedido() que maneja la lógica de actualización/creación
            Pedido pedidoActualizado = pedidoServicio.guardarPedido(pedidoDto);

            if (pedidoActualizado != null) {
                return ResponseEntity.ok(pedidoActualizado);
            } else {
                // Esto podría ocurrir si el ID es nulo o no se encuentra el recurso
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error al actualizar el pedido (Datos Inválidos): " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error interno al actualizar el pedido: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE: Eliminar un pedido por ID
    // URL: http://localhost:8080/inventario-app/pedidos/{id}
    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<HttpStatus> eliminarPedido(@PathVariable Integer id) {
        Pedido pedido = pedidoServicio.buscarPedidoPorId(id);
        if (pedido != null) {
            pedidoServicio.eliminarPedido(pedido);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }
}