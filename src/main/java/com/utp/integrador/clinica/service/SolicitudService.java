package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.model.Solicitud;
import java.util.List;

public interface SolicitudService {

    List<Solicitud> getAllSolicitudes();

    Solicitud getSolicitudById(Integer id);

    Solicitud saveSolicitud(Solicitud solicitud);

    void deleteSolicitud(Integer id);

    Solicitud aprobarSolicitud(Integer id);

    Solicitud rechazarSolicitud(Integer id, String motivo);

    Solicitud despacharSolicitud(Integer id);

    Solicitud recibirSolicitud(Integer id);
}
