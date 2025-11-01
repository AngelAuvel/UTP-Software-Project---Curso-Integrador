package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.model.Solicitud;
import com.utp.integrador.clinica.model.Departamento;
import com.utp.integrador.clinica.model.Producto;
import com.utp.integrador.clinica.model.DetalleSolicitud;
import com.utp.integrador.clinica.model.Usuario;
import com.utp.integrador.clinica.dto.request.SolicitudRequest;
import com.utp.integrador.clinica.dto.request.DetalleSolicitudRequest;
import com.utp.integrador.clinica.service.SolicitudService;
import com.utp.integrador.clinica.service.DepartamentoService;
import com.utp.integrador.clinica.service.ProductoService;
import com.utp.integrador.clinica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // Importar @Valid

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService; // Inyectar UsuarioService

    @GetMapping
    public List<Solicitud> getAllSolicitudes() {
        return solicitudService.getAllSolicitudes();
    }

    @GetMapping("/{id}")
    public Solicitud getSolicitudById(@PathVariable Integer id) {
        return solicitudService.getSolicitudById(id);
    }

    @PostMapping
    public Solicitud saveSolicitud(@Valid @RequestBody SolicitudRequest solicitudRequest) { // Añadido @Valid aquí
        // Obtener el usuario autenticado del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // El username es el email
        Usuario solicitante = usuarioService.getUsuarioByEmail(username);

        Solicitud solicitud = new Solicitud();
        solicitud.setFechaRequerida(solicitudRequest.getFechaRequerida());
        solicitud.setPrioridad(solicitudRequest.getPrioridad());
        solicitud.setJustificacion(solicitudRequest.getJustificacion());
        solicitud.setCostoTotalEstimado(solicitudRequest.getCostoTotalEstimado());
        solicitud.setOrigen(solicitudRequest.getOrigen());

        // Asignar el solicitante y su departamento
        solicitud.setSolicitante(solicitante);
        solicitud.setDepartamento(solicitante.getDepartamento()); // Asignar el departamento del usuario

        // Convertir DetalleSolicitudRequest a DetalleSolicitud
        List<DetalleSolicitud> detalles = new ArrayList<>();
        for (DetalleSolicitudRequest detalleRequest : solicitudRequest.getDetalles()) {
            DetalleSolicitud detalle = new DetalleSolicitud();
            Producto producto = productoService.getProductoById(detalleRequest.getProductoId());
            detalle.setProducto(producto);
            detalle.setCantidadSolicitada(detalleRequest.getCantidadSolicitada());
            detalle.setEspecificaciones(detalleRequest.getEspecificaciones());
            detalle.setSolicitud(solicitud); // Establecer la relación bidireccional
            detalles.add(detalle);
        }
        solicitud.setDetalles(detalles);

        return solicitudService.saveSolicitud(solicitud);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_LOGISTICA')")
    public void deleteSolicitud(@PathVariable Integer id) {
        solicitudService.deleteSolicitud(id);
    }

    @PostMapping("/{id}/aprobar")
    @PreAuthorize("hasRole('ADMIN_LOGISTICA')")
    public ResponseEntity<Solicitud> aprobarSolicitud(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitudService.aprobarSolicitud(id));
    }

    @PostMapping("/{id}/rechazar")
    @PreAuthorize("hasRole('ADMIN_LOGISTICA')")
    public ResponseEntity<Solicitud> rechazarSolicitud(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(solicitudService.rechazarSolicitud(id, body.get("motivo")));
    }

    @PostMapping("/{id}/despachar")
    @PreAuthorize("hasRole('PERSONAL_ALMACEN')")
    public ResponseEntity<Solicitud> despacharSolicitud(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitudService.despacharSolicitud(id));
    }

    @PostMapping("/{id}/recibir")
    @PreAuthorize("hasAnyRole('JEFE_DEPARTAMENTO', 'EMPLEADO_GENERAL')")
    public ResponseEntity<Solicitud> recibirSolicitud(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitudService.recibirSolicitud(id));
    }
}
