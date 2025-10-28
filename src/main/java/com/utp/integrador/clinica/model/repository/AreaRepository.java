package com.utp.integrador.clinica.model.repository;

import com.utp.integrador.clinica.model.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
}
