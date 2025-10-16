package gm.inventarios.repositorio;
import gm.inventarios.modelo.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PedidoDetalleRepositorio extends JpaRepository<PedidoDetalle, Integer> { }