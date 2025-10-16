package gm.inventarios.servicio;
import gm.inventarios.modelo.Articulo;
import java.util.List;
public interface IArticuloServicio {
    List<Articulo> listarArticulos();
    Articulo buscarArticuloPorId(Integer idArticulo);
    Articulo guardarArticulo(Articulo articulo);
    void eliminarArticuloPorId(Integer idArticulo);
}