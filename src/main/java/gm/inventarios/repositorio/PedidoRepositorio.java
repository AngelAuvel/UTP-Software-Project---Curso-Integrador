package gm.inventarios.repositorio;

import gm.inventarios.modelo.Pedido;
import gm.inventarios.modelo.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// The entity type is Pedido, and the ID type is Integer
public interface PedidoRepositorio extends JpaRepository<Pedido, Integer> {

    List<PedidoDetalle> findByPedidoId(Long idPedido);

}
