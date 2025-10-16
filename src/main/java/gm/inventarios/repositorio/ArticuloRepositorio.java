package gm.inventarios.repositorio;
import gm.inventarios.modelo.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ArticuloRepositorio extends JpaRepository<Articulo, Integer> { }