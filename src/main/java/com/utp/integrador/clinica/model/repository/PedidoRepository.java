package com.utp.integrador.clinica.model.repository;

import com.utp.integrador.clinica.model.entities.EstadoPedido;
import com.utp.integrador.clinica.model.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    long countByEstado(EstadoPedido estado);
}
