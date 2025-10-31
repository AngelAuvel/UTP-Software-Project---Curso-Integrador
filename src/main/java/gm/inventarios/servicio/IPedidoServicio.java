package gm.inventarios.servicio;

import gm.inventarios.dto.PedidoDTO;
import gm.inventarios.modelo.Pedido;
import java.util.List;

public interface IPedidoServicio {

    // Lista todos los pedidos
    public List<Pedido> listarPedidos();

    // Busca un pedido por su ID
    public Pedido buscarPedidoPorId(Integer idPedido);

    // Guarda o actualiza un pedido usando un DTO
    public Pedido guardarPedido(PedidoDTO pedidoDTO); // <--- CAMBIAR Pedido A PedidoDTO

    // Elimina un pedido
    public void eliminarPedido(Pedido pedido);
}
