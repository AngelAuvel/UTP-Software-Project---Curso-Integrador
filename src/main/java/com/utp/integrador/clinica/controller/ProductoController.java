package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.model.Producto;
import com.utp.integrador.clinica.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public Page<Producto> getAllProductos(Pageable pageable, @RequestParam(required = false) String query) {
        return productoService.getAllProductos(pageable, query);
    }

    @GetMapping("/all")
    public List<Producto> getAllProductos() {
        return productoService.getAllProductosSinPaginar();
    }

    @GetMapping("/{id}")
    public Producto getProductoById(@PathVariable Integer id) {
        return productoService.getProductoById(id);
    }

    @PostMapping
    public Producto saveProducto(@RequestBody Producto producto) {
        return productoService.saveProducto(producto);
    }

    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        // Asegúrate de que el ID del producto en el cuerpo coincida con el ID del path
        producto.setId(id);
        return productoService.updateProducto(producto);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Integer id) {
        productoService.deleteProducto(id);
    }
}
