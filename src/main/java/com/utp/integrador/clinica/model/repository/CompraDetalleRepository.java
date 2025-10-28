package com.utp.integrador.clinica.model.repository;

import com.utp.integrador.clinica.model.entities.CompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraDetalleRepository extends JpaRepository<CompraDetalle, Long> {
}
