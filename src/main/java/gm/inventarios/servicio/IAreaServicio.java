package gm.inventarios.servicio;
import gm.inventarios.modelo.Area;
import java.util.List;
public interface IAreaServicio {
    List<Area> listarAreas();
    Area buscarAreaPorId(Integer idArea);
    Area guardarArea(Area area);
    void eliminarAreaPorId(Integer idArea);
}