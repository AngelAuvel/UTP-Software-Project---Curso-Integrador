package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.dto.CategoriaDto;
import com.utp.integrador.clinica.model.entities.Categoria;
import com.utp.integrador.clinica.model.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaDto> findAll() {
        return categoriaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaDto findById(Long id) {
        return categoriaRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Transactional
    public CategoriaDto save(CategoriaDto categoriaDto) {
        Categoria categoria = convertToEntity(categoriaDto);
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return convertToDto(savedCategoria);
    }

    @Transactional
    public CategoriaDto update(Long id, CategoriaDto categoriaDto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setNombre(categoriaDto.nombre());
        categoria.setDescripcion(categoriaDto.descripcion());

        Categoria updatedCategoria = categoriaRepository.save(categoria);
        return convertToDto(updatedCategoria);
    }

    @Transactional
    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }

    private CategoriaDto convertToDto(Categoria categoria) {
        return new CategoriaDto(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }

    private Categoria convertToEntity(CategoriaDto categoriaDto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDto.nombre());
        categoria.setDescripcion(categoriaDto.descripcion());
        return categoria;
    }
}
