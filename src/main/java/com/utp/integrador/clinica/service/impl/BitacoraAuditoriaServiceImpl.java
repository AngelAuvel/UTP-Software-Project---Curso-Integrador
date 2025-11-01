package com.utp.integrador.clinica.service.impl;

import com.utp.integrador.clinica.model.BitacoraAuditoria;
import com.utp.integrador.clinica.repository.BitacoraAuditoriaRepository;
import com.utp.integrador.clinica.service.BitacoraAuditoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BitacoraAuditoriaServiceImpl implements BitacoraAuditoriaService {

    @Autowired
    private BitacoraAuditoriaRepository bitacoraAuditoriaRepository;

    @Override
    public List<BitacoraAuditoria> getAllBitacoraAuditorias() {
        return bitacoraAuditoriaRepository.findAll();
    }

    @Override
    public BitacoraAuditoria getBitacoraAuditoriaById(Integer id) {
        return bitacoraAuditoriaRepository.findById(id).orElse(null);
    }

    @Override
    public BitacoraAuditoria saveBitacoraAuditoria(BitacoraAuditoria bitacoraAuditoria) {
        return bitacoraAuditoriaRepository.save(bitacoraAuditoria);
    }

    @Override
    public void deleteBitacoraAuditoria(Integer id) {
        bitacoraAuditoriaRepository.deleteById(id);
    }
}
