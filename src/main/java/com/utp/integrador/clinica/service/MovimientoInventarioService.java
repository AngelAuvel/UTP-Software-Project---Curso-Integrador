package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.model.MovimientoInventario;
import java.util.List;

public interface MovimientoInventarioService {

    List<MovimientoInventario> getAllMovimientoInventarios();

    MovimientoInventario getMovimientoInventarioById(Integer id);

    MovimientoInventario saveMovimientoInventario(MovimientoInventario movimientoInventario);

    void deleteMovimientoInventario(Integer id);
}
