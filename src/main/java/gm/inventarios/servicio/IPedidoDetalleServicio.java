package gm.inventarios.servicio;
import gm.inventarios.modelo.PedidoDetalle;
import java.util.List;
public interface IPedidoDetalleServicio {
    List<PedidoDetalle> listarDetallesPedido();
    PedidoDetalle buscarDetallePedidoPorId(Integer idDetalle);
}