package gm.inventarios.servicio;
import gm.inventarios.modelo.CompraDetalle;
import java.util.List;
public interface ICompraDetalleServicio {
    List<CompraDetalle> listarDetallesCompra();
    CompraDetalle buscarDetalleCompraPorId(Integer idDetalle);
}