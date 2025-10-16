package gm.inventarios.controlador;

import gm.inventarios.modelo.Pedido;
import gm.inventarios.servicio.IPedidoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Pedido guardarPedido(@RequestBody Pedido pedido) {
        logger.info("Guardando pedido: " + pedido);
        return pedidoServicio.guardarPedido(pedido);
    }

    // PUT: Actualizar un pedido existente
    // URL: http://localhost:8080/inventario-app/pedidos/{id}
    @PutMapping("/pedidos/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Integer id, @RequestBody Pedido pedidoRecibido) {
        try {
            // 1. Verifica que el pedido exista
            Pedido pedidoExistente = pedidoServicio.buscarPedidoPorId(id);
            if (pedidoExistente == null) {
                return ResponseEntity.notFound().build();
            }

            // 2. Establece el ID de la ruta en el objeto recibido
            pedidoRecibido.setId(id);

            // 3. Guarda (Actualiza) los cambios
            Pedido pedidoActualizado = pedidoServicio.guardarPedido(pedidoRecibido);
            return ResponseEntity.ok(pedidoActualizado);

        } catch (NoSuchElementException e) {
            // Captura si el servicio lanza la excepción
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar un pedido por ID
    // URL: http://localhost:8080/inventario-app/pedidos/{id}
    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
        try {
            pedidoServicio.eliminarPedidoPorId(id);
            // Devuelve 204 No Content para indicar eliminación exitosa
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            // Devuelve 404 Not Found si no existe el pedido
            return ResponseEntity.notFound().build();
        }
    }
}