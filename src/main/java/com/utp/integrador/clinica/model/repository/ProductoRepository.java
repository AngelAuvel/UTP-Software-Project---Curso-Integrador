package com.utp.integrador.clinica.model.repository;

import com.utp.integrador.clinica.model.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query("SELECT count(p) FROM Producto p WHERE p.cantidadStock <= p.stockMinimo")
    long countByStockBajo();
}
