package gm.inventarios.servicio;
import gm.inventarios.modelo.PedidoDetalle;
import gm.inventarios.repositorio.PedidoDetalleRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class PedidoDetalleServicio implements IPedidoDetalleServicio {
    private final PedidoDetalleRepositorio pedidoDetalleRepositorio;
    public PedidoDetalleServicio(PedidoDetalleRepositorio repo) {
        this.pedidoDetalleRepositorio = repo;
    }

    @Override
    public List<PedidoDetalle> listarDetallesPedido() {
        return pedidoDetalleRepositorio.findAll();
    }
    @Override
    public PedidoDetalle buscarDetallePedidoPorId(Integer idDetalle) {
        return pedidoDetalleRepositorio.findById(idDetalle).orElse(null);
    }
}