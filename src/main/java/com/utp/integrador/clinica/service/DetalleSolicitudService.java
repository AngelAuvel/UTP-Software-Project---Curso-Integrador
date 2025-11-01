package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.model.DetalleSolicitud;
import java.util.List;

public interface DetalleSolicitudService {

    List<DetalleSolicitud> getAllDetalleSolicitudes();

    DetalleSolicitud getDetalleSolicitudById(Integer id);

    DetalleSolicitud saveDetalleSolicitud(DetalleSolicitud detalleSolicitud);

    void deleteDetalleSolicitud(Integer id);
}
