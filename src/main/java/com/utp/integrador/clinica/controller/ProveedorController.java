package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.dto.ProveedorDto;
import com.utp.integrador.clinica.service.ProveedorService;
import java.util.List;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public ResponseEntity<List<ProveedorDto>> getAllProveedores() {
        return ResponseEntity.ok(proveedorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDto> getProveedorById(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<ProveedorDto> createProveedor(@RequestBody ProveedorDto proveedorDto) {
        return new ResponseEntity<>(proveedorService.save(proveedorDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<ProveedorDto> updateProveedor(@PathVariable Long id, @RequestBody ProveedorDto proveedorDto) {
        return ResponseEntity.ok(proveedorService.update(id, proveedorDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        proveedorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
