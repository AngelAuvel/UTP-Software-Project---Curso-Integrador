package gm.inventarios.servicio;
import gm.inventarios.modelo.Pedido;
import java.util.List;
public interface IPedidoServicio {
    List<Pedido> listarPedidos();
    Pedido buscarPedidoPorId(Integer idPedido);
    Pedido guardarPedido(Pedido pedido);
    void eliminarPedidoPorId(Integer idPedido);
}