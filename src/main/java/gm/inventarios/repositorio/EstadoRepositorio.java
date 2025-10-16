package gm.inventarios.repositorio;
import gm.inventarios.modelo.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EstadoRepositorio extends JpaRepository<Estado, Integer> { }