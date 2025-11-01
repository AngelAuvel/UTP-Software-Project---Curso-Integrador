package com.utp.integrador.clinica.service.impl;

import com.utp.integrador.clinica.model.Producto;
import com.utp.integrador.clinica.repository.ProductoRepository;
import com.utp.integrador.clinica.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Page<Producto> getAllProductos(Pageable pageable, String query) {
        if (query != null && !query.isEmpty()) {
            return productoRepository.findByNombreOrCodigo(query, pageable);
        }
        return productoRepository.findAll(pageable);
    }

    @Override
    public List<Producto> getAllProductosSinPaginar() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProductoById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProducto(Producto producto) {
        // Verifica si el producto existe antes de actualizar
        if (producto.getId() != null && productoRepository.existsById(producto.getId())) {
            return productoRepository.save(producto);
        } else {
            // Podrías lanzar una excepción o devolver null si el producto no existe
            // Por ahora, devolveremos null o podrías manejarlo según tu lógica de negocio
            return null; // O lanzar new ResourceNotFoundException("Producto no encontrado con ID: " + producto.getId());
        }
    }

    @Override
    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }
}
