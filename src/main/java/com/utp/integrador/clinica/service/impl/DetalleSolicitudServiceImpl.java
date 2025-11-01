package com.utp.integrador.clinica.service.impl;

import com.utp.integrador.clinica.model.DetalleSolicitud;
import com.utp.integrador.clinica.repository.DetalleSolicitudRepository;
import com.utp.integrador.clinica.service.DetalleSolicitudService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleSolicitudServiceImpl implements DetalleSolicitudService {

    @Autowired
    private DetalleSolicitudRepository detalleSolicitudRepository;

    @Override
    public List<DetalleSolicitud> getAllDetalleSolicitudes() {
        return detalleSolicitudRepository.findAll();
    }

    @Override
    public DetalleSolicitud getDetalleSolicitudById(Integer id) {
        return detalleSolicitudRepository.findById(id).orElse(null);
    }

    @Override
    public DetalleSolicitud saveDetalleSolicitud(DetalleSolicitud detalleSolicitud) {
        return detalleSolicitudRepository.save(detalleSolicitud);
    }

    @Override
    public void deleteDetalleSolicitud(Integer id) {
        detalleSolicitudRepository.deleteById(id);
    }
}
