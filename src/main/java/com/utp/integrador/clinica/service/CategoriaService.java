package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.model.Categoria;
import java.util.List;

public interface CategoriaService {

    List<Categoria> getAllCategorias();

    Categoria getCategoriaById(Integer id);

    Categoria saveCategoria(Categoria categoria);

    void deleteCategoria(Integer id);
}
