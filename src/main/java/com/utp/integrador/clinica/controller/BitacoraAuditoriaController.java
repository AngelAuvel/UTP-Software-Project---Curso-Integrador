package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.model.BitacoraAuditoria;
import com.utp.integrador.clinica.service.BitacoraAuditoriaService;
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
@RequestMapping("/bitacora-auditoria")
public class BitacoraAuditoriaController {

    @Autowired
    private BitacoraAuditoriaService bitacoraAuditoriaService;

    @GetMapping
    public List<BitacoraAuditoria> getAllBitacoraAuditorias() {
        return bitacoraAuditoriaService.getAllBitacoraAuditorias();
    }

    @GetMapping("/{id}")
    public BitacoraAuditoria getBitacoraAuditoriaById(@PathVariable Integer id) {
        return bitacoraAuditoriaService.getBitacoraAuditoriaById(id);
    }

    @PostMapping
    public BitacoraAuditoria saveBitacoraAuditoria(@RequestBody BitacoraAuditoria bitacoraAuditoria) {
        return bitacoraAuditoriaService.saveBitacoraAuditoria(bitacoraAuditoria);
    }

    @DeleteMapping("/{id}")
    public void deleteBitacoraAuditoria(@PathVariable Integer id) {
        bitacoraAuditoriaService.deleteBitacoraAuditoria(id);
    }
}
