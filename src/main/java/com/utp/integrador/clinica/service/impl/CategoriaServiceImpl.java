package com.utp.integrador.clinica.service.impl;

import com.utp.integrador.clinica.model.Categoria;
import com.utp.integrador.clinica.repository.CategoriaRepository;
import com.utp.integrador.clinica.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria getCategoriaById(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void deleteCategoria(Integer id) {
        categoriaRepository.deleteById(id);
    }
}
