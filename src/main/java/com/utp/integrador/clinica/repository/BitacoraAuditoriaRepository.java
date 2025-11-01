package com.utp.integrador.clinica.repository;

import com.utp.integrador.clinica.model.BitacoraAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitacoraAuditoriaRepository extends JpaRepository<BitacoraAuditoria, Integer> {
}
