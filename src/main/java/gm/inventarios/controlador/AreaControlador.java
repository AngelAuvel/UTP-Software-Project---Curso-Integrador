package gm.inventarios.controlador;

import gm.inventarios.modelo.Area;
import gm.inventarios.servicio.IAreaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("inventario-app")
@CrossOrigin(origins = "http://localhost:4200")
public class AreaControlador {

    private final IAreaServicio areaServicio;
    private static final Logger logger = LoggerFactory.getLogger(AreaControlador.class);

    public AreaControlador(IAreaServicio areaServicio) {
        this.areaServicio = areaServicio;
    }

    // GET: Listar todas las áreas
    // URL: http://localhost:8080/inventario-app/areas
    @GetMapping("/areas")
    public List<Area> listarAreas() {
        logger.info("Listado de áreas solicitado.");
        return areaServicio.listarAreas();
    }

    // GET: Buscar área por ID
    // URL: http://localhost:8080/inventario-app/areas/{id}
    @GetMapping("/areas/{id}")
    public ResponseEntity<Area> buscarAreaPorId(@PathVariable Integer id) {
        Area area = areaServicio.buscarAreaPorId(id);
        if (area == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(area);
    }

    // POST: Crear nueva área
    // URL: http://localhost:8080/inventario-app/areas
    @PostMapping("/areas")
    public Area guardarArea(@RequestBody Area area) {
        logger.info("Guardando área: " + area);
        return areaServicio.guardarArea(area);
    }

    // PUT: Actualizar área
    // URL: http://localhost:8080/inventario-app/areas/{id}
    @PutMapping("/areas/{id}")
    public ResponseEntity<Area> actualizarArea(@PathVariable Integer id, @RequestBody Area areaRecibida) {
        try {
            Area areaExistente = areaServicio.buscarAreaPorId(id);
            if (areaExistente == null) {
                return ResponseEntity.notFound().build();
            }

            areaRecibida.setId(id);
            Area areaActualizada = areaServicio.guardarArea(areaRecibida);
            return ResponseEntity.ok(areaActualizada);

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar área
    // URL: http://localhost:8080/inventario-app/areas/{id}
    @DeleteMapping("/areas/{id}")
    public ResponseEntity<Void> eliminarArea(@PathVariable Integer id) {
        try {
            areaServicio.eliminarAreaPorId(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}