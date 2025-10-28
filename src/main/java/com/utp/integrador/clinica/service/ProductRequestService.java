package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.dto.PedidoDetalleDto;
import com.utp.integrador.clinica.dto.PedidoDto;
import com.utp.integrador.clinica.model.entities.EstadoPedido;
import com.utp.integrador.clinica.model.entities.MovimientoInventario;
import com.utp.integrador.clinica.model.entities.Pedido;
import com.utp.integrador.clinica.model.entities.PedidoDetalle;
import com.utp.integrador.clinica.model.entities.Producto;
import com.utp.integrador.clinica.model.entities.TipoMovimiento;
import com.utp.integrador.clinica.model.entities.Usuario;
import com.utp.integrador.clinica.model.repository.MovimientoInventarioRepository;
import com.utp.integrador.clinica.model.repository.PedidoRepository;
import com.utp.integrador.clinica.model.repository.ProductoRepository;
import com.utp.integrador.clinica.model.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductRequestService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;

    public ProductRequestService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, ProductoRepository productoRepository, MovimientoInventarioRepository movimientoInventarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
    }

    @Transactional(readOnly = true)
    public List<PedidoDto> findAll() {
        return pedidoRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public PedidoDto createPedido(PedidoDto pedidoDto, String userEmail) {
        Usuario solicitante = usuarioRepository.findByCorreo(userEmail).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setSolicitadoPor(solicitante);
        pedido.setMotivo(pedidoDto.motivo());
        pedido.setFechaSolicitud(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);

        List<PedidoDetalle> detalles = pedidoDto.detalles().stream().map(detalleDto -> {
            Producto producto = productoRepository.findById(detalleDto.productoId()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDto.cantidad());
            return detalle;
        }).collect(Collectors.toList());

        pedido.setDetalles(detalles);
        Pedido savedPedido = pedidoRepository.save(pedido);
        return convertToDto(savedPedido);
    }
    
    @Transactional
    public PedidoDto updatePedidoStatus(Long pedidoId, EstadoPedido estado, String userEmail) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Usuario respondedor = usuarioRepository.findByCorreo(userEmail).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        pedido.setEstado(estado);
        pedido.setRespondidoPor(respondedor);
        pedido.setFechaRespuesta(LocalDateTime.now());

        if (estado == EstadoPedido.APROBADO) {
            for (PedidoDetalle detalle : pedido.getDetalles()) {
                Producto producto = detalle.getProducto();
                producto.setCantidadStock(producto.getCantidadStock() - detalle.getCantidad());
                productoRepository.save(producto);

                MovimientoInventario movimiento = new MovimientoInventario();
                movimiento.setTipo(TipoMovimiento.SALIDA);
                movimiento.setProducto(producto);
                movimiento.setCantidad(detalle.getCantidad());
                movimiento.setPedido(pedido);
                movimiento.setUsuario(respondedor);
                movimiento.setFecha(LocalDateTime.now());
                movimiento.setObservaciones("Salida por pedido #" + pedido.getId());
                movimientoInventarioRepository.save(movimiento);
            }
        }

        Pedido updatedPedido = pedidoRepository.save(pedido);
        return convertToDto(updatedPedido);
    }

    private PedidoDto convertToDto(Pedido pedido) {
        List<PedidoDetalleDto> detallesDto = pedido.getDetalles().stream()
                .map(detalle -> new PedidoDetalleDto(
                        detalle.getId(),
                        detalle.getProducto().getId(),
                        detalle.getProducto().getNombre(),
                        detalle.getCantidad()))
                .collect(Collectors.toList());

        return new PedidoDto(
                pedido.getId(),
                pedido.getSolicitadoPor().getIdUsuario(),
                pedido.getSolicitadoPor().getNombres() + " " + pedido.getSolicitadoPor().getApellidos(),
                pedido.getMotivo(),
                pedido.getEstado(),
                pedido.getFechaSolicitud(),
                pedido.getFechaRespuesta(),
                pedido.getRespondidoPor() != null ? pedido.getRespondidoPor().getIdUsuario() : null,
                pedido.getRespondidoPor() != null ? pedido.getRespondidoPor().getNombres() + " " + pedido.getRespondidoPor().getApellidos() : null,
                detallesDto
        );
    }
}
