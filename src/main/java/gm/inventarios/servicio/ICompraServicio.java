package gm.inventarios.servicio;
import gm.inventarios.modelo.Compra;
import java.util.List;
public interface ICompraServicio {
    List<Compra> listarCompras();
    Compra buscarCompraPorId(Integer idCompra);
    Compra guardarCompra(Compra compra);
    void eliminarCompraPorId(Integer idCompra);
}