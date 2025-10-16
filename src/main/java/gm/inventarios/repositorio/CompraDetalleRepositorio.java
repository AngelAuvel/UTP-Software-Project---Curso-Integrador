package gm.inventarios.repositorio;
import gm.inventarios.modelo.CompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CompraDetalleRepositorio extends JpaRepository<CompraDetalle, Integer> { }