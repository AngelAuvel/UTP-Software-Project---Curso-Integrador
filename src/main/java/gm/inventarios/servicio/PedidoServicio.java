package gm.inventarios.servicio;
import gm.inventarios.modelo.Pedido;
import gm.inventarios.repositorio.PedidoRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class PedidoServicio implements IPedidoServicio {
    private final PedidoRepositorio pedidoRepositorio;
    public PedidoServicio(PedidoRepositorio pedidoRepositorio) {
        this.pedidoRepositorio = pedidoRepositorio;
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepositorio.findAll();
    }
    @Override
    public Pedido buscarPedidoPorId(Integer idPedido) {
        return pedidoRepositorio.findById(idPedido).orElse(null);
    }
    @Override
    public Pedido guardarPedido(Pedido pedido) {
        return pedidoRepositorio.save(pedido);
    }
    @Override
    public void eliminarPedidoPorId(Integer idPedido) {
        if (!pedidoRepositorio.existsById(idPedido)) {
            throw new NoSuchElementException("No existe el pedido con ID: " + idPedido);
        }
        pedidoRepositorio.deleteById(idPedido);
    }
}