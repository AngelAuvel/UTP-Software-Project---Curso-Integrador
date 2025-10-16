package gm.inventarios.controlador;

import gm.inventarios.modelo.Producto;
import gm.inventarios.repositorio.ProductoRepositorio;
import gm.inventarios.servicio.IProductoServicio;
import gm.inventarios.servicio.ProductoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("inventario-app") // Base path para todos los endpoints de este controlador
@CrossOrigin(origins = "http://localhost:4200") // Permite peticiones desde un frontend en localhost:3000 (ej. React)
public class ProductoControlador {

    private final IProductoServicio productoServicio;
    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    public ProductoControlador(IProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }
    @GetMapping("/")
    public List<Producto> listarProductos() {
        List<Producto> productos = productoServicio.listarProductos();
        logger.info("Listado de productos: " + productos);
        productos.forEach(producto -> logger.info("Producto: " + producto));
        return productos;
    }

    @GetMapping("/{id}")
    public Producto buscarProductoPorId(@PathVariable Integer id) {
        return productoServicio.buscarProductoPorId(id);
    }

    @PostMapping("/")
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoServicio.guardarProducto(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        producto.setIdProducto(id);
        return productoServicio.guardarProducto(producto);
    }

    // Endpoint para eliminar un producto por ID
    // DELETE http://localhost:8080/inventario-app/productos/{id}
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Integer id) {
        productoServicio.eliminarProductoPorId(id);
    }
}