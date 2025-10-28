package com.utp.integrador.clinica.model.repository;

import com.utp.integrador.clinica.model.entities.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
}
