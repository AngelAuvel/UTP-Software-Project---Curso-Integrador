package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.model.Departamento;
import java.util.List;

public interface DepartamentoService {

    List<Departamento> getAllDepartamentos();

    Departamento getDepartamentoById(Integer id);

    Departamento saveDepartamento(Departamento departamento);

    void deleteDepartamento(Integer id);
}
