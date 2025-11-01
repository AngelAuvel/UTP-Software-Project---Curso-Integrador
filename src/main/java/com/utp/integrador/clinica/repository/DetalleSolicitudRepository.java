package com.utp.integrador.clinica.repository;

import com.utp.integrador.clinica.model.DetalleSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleSolicitudRepository extends JpaRepository<DetalleSolicitud, Integer> {
}
