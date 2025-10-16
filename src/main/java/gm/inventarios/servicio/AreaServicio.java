package gm.inventarios.servicio;
import gm.inventarios.modelo.Area;
import gm.inventarios.repositorio.AreaRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class AreaServicio implements IAreaServicio {
    private final AreaRepositorio areaRepositorio;
    public AreaServicio(AreaRepositorio areaRepositorio) {
        this.areaRepositorio = areaRepositorio;
    }

    @Override
    public List<Area> listarAreas() {
        return areaRepositorio.findAll();
    }
    @Override
    public Area buscarAreaPorId(Integer idArea) {
        return areaRepositorio.findById(idArea).orElse(null);
    }
    @Override
    public Area guardarArea(Area area) {
        return areaRepositorio.save(area);
    }
    @Override
    public void eliminarAreaPorId(Integer idArea) {
        if (!areaRepositorio.existsById(idArea)) {
            throw new NoSuchElementException("No existe el área con ID: " + idArea);
        }
        areaRepositorio.deleteById(idArea);
    }
}