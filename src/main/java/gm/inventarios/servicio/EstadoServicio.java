package gm.inventarios.servicio;
import gm.inventarios.modelo.Estado;
import gm.inventarios.repositorio.EstadoRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class EstadoServicio implements IEstadoServicio {
    private final EstadoRepositorio estadoRepositorio;
    public EstadoServicio(EstadoRepositorio estadoRepositorio) {
        this.estadoRepositorio = estadoRepositorio;
    }

    @Override
    public List<Estado> listarEstados() {
        return estadoRepositorio.findAll();
    }
    @Override
    public Estado buscarEstadoPorId(Integer idEstado) {
        return estadoRepositorio.findById(idEstado).orElse(null);
    }
}