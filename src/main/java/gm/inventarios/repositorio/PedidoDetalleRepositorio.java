package gm.inventarios.repositorio;

import gm.inventarios.modelo.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio para la entidad PedidoDetalle.
 * Contiene el método custom para la eliminación masiva de detalles.
 */
public interface PedidoDetalleRepositorio extends JpaRepository<PedidoDetalle, Integer> {

    /**
     * IMPORTANTE: Método necesario para la edición de pedidos.
     * Elimina todos los detalles asociados a un ID de Pedido específico.
     * @param idPedido El ID del pedido cuyos detalles serán eliminados.
     */
    @Transactional // Es crucial para operaciones de modificación (DELETE, UPDATE)
    @Modifying
    @Query("DELETE FROM PedidoDetalle pd WHERE pd.pedido.id = :idPedido")
    void deleteByIdPedido(@Param("idPedido") Integer idPedido);

}
