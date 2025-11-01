package com.utp.integrador.clinica.repository;

import com.utp.integrador.clinica.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Método para buscar productos por nombre o código, con paginación
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.codigo) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Producto> findByNombreOrCodigo(@Param("query") String query, Pageable pageable);

    // Método para paginar todos los productos si no hay búsqueda
    Page<Producto> findAll(Pageable pageable);
}
