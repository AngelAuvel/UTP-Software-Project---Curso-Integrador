package com.utp.integrador.clinica.service.impl;

import com.utp.integrador.clinica.model.EstadoSolicitud;
import com.utp.integrador.clinica.model.Solicitud;
import com.utp.integrador.clinica.model.Usuario;
import com.utp.integrador.clinica.repository.SolicitudRepository;
import com.utp.integrador.clinica.repository.UsuarioRepository;
import com.utp.integrador.clinica.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Solicitud> getAllSolicitudes() {
        return solicitudRepository.findAll();
    }

    @Override
    public Solicitud getSolicitudById(Integer id) {
        return solicitudRepository.findById(id).orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

    @Override
    public Solicitud saveSolicitud(Solicitud solicitud) {
        // Lógica para asignar el solicitante actual
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario solicitante = usuarioRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        solicitud.setSolicitante(solicitante);
        solicitud.setDepartamento(solicitante.getDepartamento());
        return solicitudRepository.save(solicitud);
    }

    @Override
    public void deleteSolicitud(Integer id) {
        solicitudRepository.deleteById(id);
    }

    @Override
    public Solicitud aprobarSolicitud(Integer id) {
        Solicitud solicitud = getSolicitudById(id);
        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden aprobar solicitudes en estado PENDIENTE.");
        }
        solicitud.setEstado(EstadoSolicitud.APROBADA);
        solicitud.setFechaAprobacion(LocalDateTime.now());
        // Asignar el aprobador actual
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario aprobador = usuarioRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        solicitud.setAprobador(aprobador);
        return solicitudRepository.save(solicitud);
    }

    @Override
    public Solicitud rechazarSolicitud(Integer id, String motivo) {
        Solicitud solicitud = getSolicitudById(id);
        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden rechazar solicitudes en estado PENDIENTE.");
        }
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        solicitud.setMotivoRechazo(motivo);
        solicitud.setFechaAprobacion(LocalDateTime.now()); // Se usa la misma columna para fecha de rechazo
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario aprobador = usuarioRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        solicitud.setAprobador(aprobador);
        return solicitudRepository.save(solicitud);
    }

    @Override
    public Solicitud despacharSolicitud(Integer id) {
        Solicitud solicitud = getSolicitudById(id);
        if (solicitud.getEstado() != EstadoSolicitud.APROBADA) {
            throw new IllegalStateException("Solo se pueden despachar solicitudes en estado APROBADA.");
        }
        solicitud.setEstado(EstadoSolicitud.DESPACHADA);
        solicitud.setFechaDespacho(LocalDateTime.now());
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario despachador = usuarioRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        solicitud.setDespachador(despachador);
        // Aquí iría la lógica para crear los movimientos de inventario
        return solicitudRepository.save(solicitud);
    }

    @Override
    public Solicitud recibirSolicitud(Integer id) {
        Solicitud solicitud = getSolicitudById(id);
        if (solicitud.getEstado() != EstadoSolicitud.DESPACHADA) {
            throw new IllegalStateException("Solo se pueden recibir solicitudes en estado DESPACHADA.");
        }
        solicitud.setEstado(EstadoSolicitud.ENTREGADA);
        solicitud.setFechaRecepcion(LocalDateTime.now());
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario receptor = usuarioRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        solicitud.setReceptor(receptor);
        return solicitudRepository.save(solicitud);
    }
}
