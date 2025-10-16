package gm.inventarios.servicio;
import gm.inventarios.modelo.Articulo;
import gm.inventarios.repositorio.ArticuloRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ArticuloServicio implements IArticuloServicio {
    private final ArticuloRepositorio articuloRepositorio;
    public ArticuloServicio(ArticuloRepositorio articuloRepositorio) {
        this.articuloRepositorio = articuloRepositorio;
    }

    @Override
    public List<Articulo> listarArticulos() {
        return articuloRepositorio.findAll();
    }
    @Override
    public Articulo buscarArticuloPorId(Integer idArticulo) {
        return articuloRepositorio.findById(idArticulo).orElse(null);
    }
    @Override
    public Articulo guardarArticulo(Articulo articulo) {
        return articuloRepositorio.save(articulo);
    }
    @Override
    public void eliminarArticuloPorId(Integer idArticulo) {
        if (!articuloRepositorio.existsById(idArticulo)) {
            throw new NoSuchElementException("No existe el artículo con ID: " + idArticulo);
        }
        articuloRepositorio.deleteById(idArticulo);
    }
}