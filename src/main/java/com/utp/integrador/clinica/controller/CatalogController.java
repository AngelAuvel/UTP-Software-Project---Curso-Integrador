package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.model.entities.Area;
import com.utp.integrador.clinica.model.entities.Categoria;
import com.utp.integrador.clinica.model.repository.AreaRepository;
import com.utp.integrador.clinica.model.repository.CategoriaRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalogs")
public class CatalogController {

    private final AreaRepository areaRepository;
    private final CategoriaRepository categoriaRepository;

    public CatalogController(AreaRepository areaRepository, CategoriaRepository categoriaRepository) {
        this.areaRepository = areaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/areas")
    public ResponseEntity<List<Area>> getAreas() {
        return ResponseEntity.ok(areaRepository.findAll());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Categoria>> getCategories() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @PostMapping("/categories")
    public ResponseEntity<Categoria> createCategory(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }
}
