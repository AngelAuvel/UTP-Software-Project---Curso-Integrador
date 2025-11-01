package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {

    Page<Producto> getAllProductos(Pageable pageable, String query);

    List<Producto> getAllProductosSinPaginar();

    Producto getProductoById(Integer id);

    Producto saveProducto(Producto producto);

    Producto updateProducto(Producto producto); // <--- Nuevo método añadido

    void deleteProducto(Integer id);
}
