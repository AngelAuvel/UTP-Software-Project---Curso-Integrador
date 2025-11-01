package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.model.DetalleSolicitud;
import com.utp.integrador.clinica.service.DetalleSolicitudService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/detalle-solicitudes")
public class DetalleSolicitudController {

    @Autowired
    private DetalleSolicitudService detalleSolicitudService;

    @GetMapping
    public List<DetalleSolicitud> getAllDetalleSolicitudes() {
        return detalleSolicitudService.getAllDetalleSolicitudes();
    }

    @GetMapping("/{id}")
    public DetalleSolicitud getDetalleSolicitudById(@PathVariable Integer id) {
        return detalleSolicitudService.getDetalleSolicitudById(id);
    }

    @PostMapping
    public DetalleSolicitud saveDetalleSolicitud(@RequestBody DetalleSolicitud detalleSolicitud) {
        return detalleSolicitudService.saveDetalleSolicitud(detalleSolicitud);
    }

    @DeleteMapping("/{id}")
    public void deleteDetalleSolicitud(@PathVariable Integer id) {
        detalleSolicitudService.deleteDetalleSolicitud(id);
    }
}
