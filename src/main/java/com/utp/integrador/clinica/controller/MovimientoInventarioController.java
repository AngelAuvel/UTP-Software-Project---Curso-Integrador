package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.model.MovimientoInventario;
import com.utp.integrador.clinica.service.MovimientoInventarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimientos-inventario")
public class MovimientoInventarioController {

    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    @GetMapping
    public List<MovimientoInventario> getAllMovimientoInventarios() {
        return movimientoInventarioService.getAllMovimientoInventarios();
    }

    @GetMapping("/{id}")
    public MovimientoInventario getMovimientoInventarioById(@PathVariable Integer id) {
        return movimientoInventarioService.getMovimientoInventarioById(id);
    }

    @PostMapping
    public MovimientoInventario saveMovimientoInventario(@RequestBody MovimientoInventario movimientoInventario) {
        return movimientoInventarioService.saveMovimientoInventario(movimientoInventario);
    }

    @DeleteMapping("/{id}")
    public void deleteMovimientoInventario(@PathVariable Integer id) {
        movimientoInventarioService.deleteMovimientoInventario(id);
    }
}
