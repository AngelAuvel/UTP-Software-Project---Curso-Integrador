package gm.inventarios.servicio;
import gm.inventarios.modelo.Estado;
import java.util.List;
public interface IEstadoServicio {
    List<Estado> listarEstados();
    Estado buscarEstadoPorId(Integer idEstado);
    // CRUD limitado para tablas de catálogo
}