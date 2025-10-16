package gm.inventarios.repositorio;
import gm.inventarios.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PedidoRepositorio extends JpaRepository<Pedido, Integer> { }