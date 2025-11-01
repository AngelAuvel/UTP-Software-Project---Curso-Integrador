package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.model.Departamento;
import com.utp.integrador.clinica.service.DepartamentoService;
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
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public List<Departamento> getAllDepartamentos() {
        return departamentoService.getAllDepartamentos();
    }

    @GetMapping("/{id}")
    public Departamento getDepartamentoById(@PathVariable Integer id) {
        return departamentoService.getDepartamentoById(id);
    }

    @PostMapping
    public Departamento saveDepartamento(@RequestBody Departamento departamento) {
        return departamentoService.saveDepartamento(departamento);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartamento(@PathVariable Integer id) {
        departamentoService.deleteDepartamento(id);
    }
}
