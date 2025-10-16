package gm.inventarios.repositorio;
import gm.inventarios.modelo.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CompraRepositorio extends JpaRepository<Compra, Integer> { }