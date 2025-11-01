package com.utp.integrador.clinica.service.impl;

import com.utp.integrador.clinica.model.MovimientoInventario;
import com.utp.integrador.clinica.repository.MovimientoInventarioRepository;
import com.utp.integrador.clinica.service.MovimientoInventarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    @Override
    public List<MovimientoInventario> getAllMovimientoInventarios() {
        return movimientoInventarioRepository.findAll();
    }

    @Override
    public MovimientoInventario getMovimientoInventarioById(Integer id) {
        return movimientoInventarioRepository.findById(id).orElse(null);
    }

    @Override
    public MovimientoInventario saveMovimientoInventario(MovimientoInventario movimientoInventario) {
        return movimientoInventarioRepository.save(movimientoInventario);
    }

    @Override
    public void deleteMovimientoInventario(Integer id) {
        movimientoInventarioRepository.deleteById(id);
    }
}
