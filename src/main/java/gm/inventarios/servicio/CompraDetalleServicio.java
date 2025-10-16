package gm.inventarios.servicio;
import gm.inventarios.modelo.CompraDetalle;
import gm.inventarios.repositorio.CompraDetalleRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class CompraDetalleServicio implements ICompraDetalleServicio {
    private final CompraDetalleRepositorio compraDetalleRepositorio;
    public CompraDetalleServicio(CompraDetalleRepositorio repo) {
        this.compraDetalleRepositorio = repo;
    }

    @Override
    public List<CompraDetalle> listarDetallesCompra() {
        return compraDetalleRepositorio.findAll();
    }
    @Override
    public CompraDetalle buscarDetalleCompraPorId(Integer idDetalle) {
        return compraDetalleRepositorio.findById(idDetalle).orElse(null);
    }
}