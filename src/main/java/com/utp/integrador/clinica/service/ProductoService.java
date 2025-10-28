package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.dto.ProductoDto;
import com.utp.integrador.clinica.model.entities.Categoria;
import com.utp.integrador.clinica.model.entities.Producto;
import com.utp.integrador.clinica.model.entities.Proveedor;
import com.utp.integrador.clinica.model.repository.CategoriaRepository;
import com.utp.integrador.clinica.model.repository.ProductoRepository;
import com.utp.integrador.clinica.model.repository.ProveedorRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProveedorRepository proveedorRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, ProveedorRepository proveedorRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductoDto> findAll() {
        return productoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductoDto findById(Long id) {
        return productoRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Transactional
    public ProductoDto save(ProductoDto productoDto) {
        Producto producto = convertToEntity(productoDto);
        producto.setFechaRegistro(LocalDateTime.now());
        producto.setEstado("ACTIVO");
        Producto savedProducto = productoRepository.save(producto);
        return convertToDto(savedProducto);
    }

    @Transactional
    public ProductoDto update(Long id, ProductoDto productoDto) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        Categoria categoria = categoriaRepository.findById(productoDto.categoriaId()).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Proveedor proveedor = proveedorRepository.findById(productoDto.proveedorId()).orElse(null);

        producto.setNombre(productoDto.nombre());
        producto.setCategoria(categoria);
        producto.setCantidadStock(productoDto.cantidadStock());
        producto.setStockMinimo(productoDto.stockMinimo());
        producto.setPrecioTotal(productoDto.precioTotal());

        if (productoDto.precioTotal() != null && productoDto.cantidadStock() > 0) {
            BigDecimal precioUnitario = productoDto.precioTotal().divide(BigDecimal.valueOf(productoDto.cantidadStock()), 2, RoundingMode.HALF_UP);
            producto.setPrecio(precioUnitario);
        } else {
            producto.setPrecio(productoDto.precio()); // Mantener el precio anterior o el enviado si no hay cálculo
        }

        producto.setCodigo(productoDto.codigo());
        producto.setProveedor(proveedor);
        producto.setDescripcion(productoDto.descripcion());
        producto.setEstado(productoDto.estado());

        Producto updatedProducto = productoRepository.save(producto);
        return convertToDto(updatedProducto);
    }

    @Transactional
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    private ProductoDto convertToDto(Producto producto) {
        return new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria() != null ? producto.getCategoria().getId() : null,
                producto.getCategoria() != null ? producto.getCategoria().getNombre() : null,
                producto.getCantidadStock(),
                producto.getStockMinimo(),
                producto.getPrecio(),
                producto.getPrecioTotal(),
                producto.getCodigo(),
                producto.getProveedor() != null ? producto.getProveedor().getId() : null,
                producto.getProveedor() != null ? producto.getProveedor().getNombre() : null,
                producto.getDescripcion(),
                producto.getEstado(),
                producto.getFechaRegistro()
        );
    }

    private Producto convertToEntity(ProductoDto productoDto) {
        Producto producto = new Producto();
        Categoria categoria = categoriaRepository.findById(productoDto.categoriaId()).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Proveedor proveedor = proveedorRepository.findById(productoDto.proveedorId()).orElse(null);

        producto.setNombre(productoDto.nombre());
        producto.setCategoria(categoria);
        producto.setCantidadStock(productoDto.cantidadStock());
        producto.setStockMinimo(productoDto.stockMinimo());
        producto.setPrecioTotal(productoDto.precioTotal());

        if (productoDto.precioTotal() != null && productoDto.cantidadStock() > 0) {
            BigDecimal precioUnitario = productoDto.precioTotal().divide(BigDecimal.valueOf(productoDto.cantidadStock()), 2, RoundingMode.HALF_UP);
            producto.setPrecio(precioUnitario);
        } else {
            producto.setPrecio(productoDto.precio());
        }

        producto.setCodigo(productoDto.codigo());
        producto.setProveedor(proveedor);
        producto.setDescripcion(productoDto.descripcion());
        producto.setEstado(productoDto.estado());
        
        return producto;
    }
}
