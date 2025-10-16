package gm.inventarios.servicio;
import gm.inventarios.modelo.Compra;
import gm.inventarios.repositorio.CompraRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CompraServicio implements ICompraServicio {
    private final CompraRepositorio compraRepositorio;
    public CompraServicio(CompraRepositorio compraRepositorio) {
        this.compraRepositorio = compraRepositorio;
    }

    @Override
    public List<Compra> listarCompras() {
        return compraRepositorio.findAll();
    }
    @Override
    public Compra buscarCompraPorId(Integer idCompra) {
        return compraRepositorio.findById(idCompra).orElse(null);
    }
    @Override
    public Compra guardarCompra(Compra compra) {
        return compraRepositorio.save(compra);
    }
    @Override
    public void eliminarCompraPorId(Integer idCompra) {
        if (!compraRepositorio.existsById(idCompra)) {
            throw new NoSuchElementException("No existe la compra con ID: " + idCompra);
        }
        compraRepositorio.deleteById(idCompra);
    }
}