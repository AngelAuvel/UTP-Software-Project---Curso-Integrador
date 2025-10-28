package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.dto.DashboardStatsDto;
import com.utp.integrador.clinica.model.entities.EstadoPedido;
import com.utp.integrador.clinica.model.repository.MovimientoInventarioRepository;
import com.utp.integrador.clinica.model.repository.PedidoRepository;
import com.utp.integrador.clinica.model.repository.ProductoRepository;
import com.utp.integrador.clinica.model.repository.ProveedorRepository;
import com.utp.integrador.clinica.model.repository.UsuarioRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final PedidoRepository pedidoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;

    public DashboardService(UsuarioRepository usuarioRepository, ProductoRepository productoRepository, ProveedorRepository proveedorRepository, PedidoRepository pedidoRepository, MovimientoInventarioRepository movimientoInventarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.pedidoRepository = pedidoRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
    }

    @Transactional(readOnly = true)
    public DashboardStatsDto getDashboardStats() {
        long totalUsuarios = usuarioRepository.count();
        long totalProductos = productoRepository.count();
        long totalProveedores = proveedorRepository.count();
        long solicitudesPendientes = pedidoRepository.countByEstado(EstadoPedido.PENDIENTE);
        long productosStockBajo = productoRepository.countByStockBajo();
        
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        long movimientosMes = movimientoInventarioRepository.countByFechaBetween(startOfMonth, endOfMonth);
        
        BigDecimal valorInventario = productoRepository.findAll().stream()
                .map(p -> p.getPrecio().multiply(BigDecimal.valueOf(p.getCantidadStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DashboardStatsDto(
                totalUsuarios,
                totalProductos,
                totalProveedores,
                solicitudesPendientes,
                productosStockBajo,
                movimientosMes,
                valorInventario
        );
    }
}
