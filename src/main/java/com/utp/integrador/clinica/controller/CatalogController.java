package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.model.entities.Area;
import com.utp.integrador.clinica.model.entities.Categoria;
import com.utp.integrador.clinica.model.repository.AreaRepository;
import com.utp.integrador.clinica.model.repository.CategoriaRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CatalogController {

    private final AreaRepository areaRepository;
    private final CategoriaRepository categoriaRepository;

    public CatalogController(AreaRepository areaRepository, CategoriaRepository categoriaRepository) {
        this.areaRepository = areaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // --- Endpoints para Áreas (Solo ADMIN) ---

    @GetMapping("/areas")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Area>> getAreas() {
        return ResponseEntity.ok(areaRepository.findAll());
    }

    @PostMapping("/areas")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Area> createArea(@RequestBody Area area) {
        return ResponseEntity.ok(areaRepository.save(area));
    }

    @PutMapping("/areas/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Area> updateArea(@PathVariable Long id, @RequestBody Area areaDetails) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area no encontrada con id: " + id));
        area.setNombre(areaDetails.getNombre());
        return ResponseEntity.ok(areaRepository.save(area));
    }

    @DeleteMapping("/areas/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteArea(@PathVariable Long id) {
        areaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoints para Categorías (ADMIN y LOGISTICA) ---

    @GetMapping("/categorias")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<List<Categoria>> getCategories() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @PostMapping("/categorias")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<Categoria> createCategory(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @PutMapping("/categorias/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<Categoria> updateCategory(@PathVariable Long id, @RequestBody Categoria categoriaDetails) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
        categoria.setNombre(categoriaDetails.getNombre());
        categoria.setDescripcion(categoriaDetails.getDescripcion());
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @DeleteMapping("/categorias/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
