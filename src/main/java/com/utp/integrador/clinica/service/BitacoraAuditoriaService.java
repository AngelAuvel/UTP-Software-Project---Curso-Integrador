package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.model.BitacoraAuditoria;
import java.util.List;

public interface BitacoraAuditoriaService {

    List<BitacoraAuditoria> getAllBitacoraAuditorias();

    BitacoraAuditoria getBitacoraAuditoriaById(Integer id);

    BitacoraAuditoria saveBitacoraAuditoria(BitacoraAuditoria bitacoraAuditoria);

    void deleteBitacoraAuditoria(Integer id);
}
