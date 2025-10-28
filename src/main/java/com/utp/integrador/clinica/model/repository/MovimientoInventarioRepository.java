package com.utp.integrador.clinica.model.repository;

import com.utp.integrador.clinica.model.entities.MovimientoInventario;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    long countByFechaBetween(LocalDateTime start, LocalDateTime end);
}
