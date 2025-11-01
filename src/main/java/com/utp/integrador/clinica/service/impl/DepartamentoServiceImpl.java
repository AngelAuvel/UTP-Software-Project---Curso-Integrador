package com.utp.integrador.clinica.service.impl;

import com.utp.integrador.clinica.model.Departamento;
import com.utp.integrador.clinica.repository.DepartamentoRepository;
import com.utp.integrador.clinica.service.DepartamentoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Override
    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    @Override
    public Departamento getDepartamentoById(Integer id) {
        return departamentoRepository.findById(id).orElse(null);
    }

    @Override
    public Departamento saveDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    @Override
    public void deleteDepartamento(Integer id) {
        departamentoRepository.deleteById(id);
    }
}
