package gm.inventarios.servicio;

import gm.inventarios.dto.PedidoDTO;
import gm.inventarios.dto.PedidoDetalleDTO;
import gm.inventarios.modelo.Pedido;
import gm.inventarios.modelo.PedidoDetalle;
import gm.inventarios.modelo.Area;
import gm.inventarios.modelo.Estado;
import gm.inventarios.modelo.Usuario;
import gm.inventarios.modelo.Articulo;
import gm.inventarios.repositorio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoServicio implements IPedidoServicio {

    private static final Logger logger = LoggerFactory.getLogger(PedidoServicio.class);

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private PedidoDetalleRepositorio pedidoDetalleRepositorio;

    // Repositorios para Lookups de entidades relacionadas
    @Autowired
    private AreaRepositorio areaRepositorio;
    @Autowired
    private EstadoRepositorio estadoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ArticuloRepositorio articuloRepositorio;

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepositorio.findAll();
    }

    @Override
    @Transactional
    public Pedido guardarPedido(PedidoDTO pedidoDto) {
        logger.info("--- INICIO de guardado/actualización de PedidoDTO ---");

        // 1. Mapear DTO a Entidad (Cabecera)
        Pedido pedidoEntity = mapDtoToEntity(pedidoDto);

        // 2. Guardar la Cabecera (Esto genera el ID para nuevos pedidos)
        Pedido savedPedido = pedidoRepositorio.save(pedidoEntity);
        logger.info("Cabecera del Pedido guardada/actualizada. ID asignado: {}", savedPedido.getId());


        // 3. Lógica para guardar los detalles
        if (pedidoDto.getItems() != null && !pedidoDto.getItems().isEmpty()) {

            logger.info("Guardando {} líneas de detalle para el Pedido ID: {}",
                    pedidoDto.getItems().size(), savedPedido.getId());

            // Procesamos y persistimos cada detalle individualmente
            for (PedidoDetalleDTO detalleDTO : pedidoDto.getItems()) {
                PedidoDetalle detalleEntity = mapDetalleDtoToEntity(detalleDTO, savedPedido);
                pedidoDetalleRepositorio.save(detalleEntity);
            }
        }

        logger.info("--- FIN de guardado/actualización de PedidoDTO (ID: {}) ---", savedPedido.getId());
        return savedPedido;
    }

    @Override
    public Pedido buscarPedidoPorId(Integer idPedido) {
        if (idPedido == null) {
            logger.warn("Se intentó buscar un Pedido con ID nulo.");
            return null;
        }
        return pedidoRepositorio.findById(idPedido).orElse(null);
    }

    @Override
    public void eliminarPedido(Pedido pedido) {
        logger.info("Eliminando pedido con ID: {}", pedido.getId());
        pedidoRepositorio.delete(pedido);
        logger.info("Pedido eliminado con éxito.");
    }

    // --- MÉTODOS DE MAPEO: DTO a ENTITY ---

    /**
     * Mapea PedidoDTO a Pedido, realizando Lookups (búsquedas) de las entidades
     * de relación (Area, Estado, Usuario)
     */
    private Pedido mapDtoToEntity(PedidoDTO dto) {
        Pedido entity = new Pedido();
        entity.setId(dto.getId());

        // ********** CORRECCIÓN DE FECHAS **********
        // Asumiendo que PedidoDTO.getFechaSolicitud() ahora devuelve LocalDate
        // (porque el compilador rechaza .isEmpty())
        try {
            // Ya no es necesario .parse() ni .isEmpty()
            if (dto.getFechaSolicitud() != null) {
                entity.setFechaSolicitud(dto.getFechaSolicitud());
            }
            if (dto.getFechaAprobacion() != null) {
                entity.setFechaAprobacion(dto.getFechaAprobacion());
            }
        } catch (Exception e) { // Cambié el catch a Exception ya que DateTimeParseException ya no aplica
            // Si hay alguna excepción al mapear o usar el getter, la capturamos.
            logger.error("Error al asignar fecha en PedidoDTO: {}", e.getMessage());
            throw new RuntimeException("Error al procesar campos de fecha: " + e.getMessage());
        }
        // *************************************************************************

        // --- Mapeo de Lookups (Búsqueda de la Entidad completa por ID) ---

        // 1. Área
        if (dto.getIdArea() != null) {
            Area area = areaRepositorio.findById(dto.getIdArea())
                    .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + dto.getIdArea()));
            entity.setArea(area);
        }

        // 2. Estado
        if (dto.getIdEstado() != null) {
            Estado estado = estadoRepositorio.findById(dto.getIdEstado())
                    .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + dto.getIdEstado()));
            entity.setEstado(estado);
        }

        // 3. Usuario
        if (dto.getIdUsuario() != null) {
            Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getIdUsuario()));
            entity.setUsuario(usuario);
        }

        return entity;
    }

    /**
     * Mapea PedidoDetalleDTO a PedidoDetalle, realizando Lookup (búsqueda) del Articulo.
     * @param pedido Es la entidad Pedido principal ya guardada o en proceso de guardado.
     */
    private PedidoDetalle mapDetalleDtoToEntity(PedidoDetalleDTO dto, Pedido pedido) {
        PedidoDetalle entity = new PedidoDetalle();

        entity.setId(dto.getId());
        entity.setCantidad(dto.getCantidad());
        entity.setGlosa(dto.getGlosa());

        // Establecer la referencia al pedido padre (CRUCIAL)
        entity.setPedido(pedido);

        // Mapeo de la relación a Articulo (Lookup)
        if (dto.getIdArticulo() != null) {
            Articulo articulo = articuloRepositorio.findById(dto.getIdArticulo())
                    .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + dto.getIdArticulo()));
            entity.setArticulo(articulo);
        } else {
            throw new IllegalArgumentException("ID de Artículo es obligatorio para el detalle.");
        }
        return entity;
    }
}
