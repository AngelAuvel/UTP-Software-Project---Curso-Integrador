package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.dto.ProveedorDto;
import com.utp.integrador.clinica.service.ProviderService;
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
@RequestMapping("/api/providers")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<List<ProveedorDto>> getAllProviders() {
        return ResponseEntity.ok(providerService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<ProveedorDto> getProviderById(@PathVariable Long id) {
        return ResponseEntity.ok(providerService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<ProveedorDto> createProvider(@RequestBody ProveedorDto proveedorDto) {
        return new ResponseEntity<>(providerService.save(proveedorDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<ProveedorDto> updateProvider(@PathVariable Long id, @RequestBody ProveedorDto proveedorDto) {
        return ResponseEntity.ok(providerService.update(id, proveedorDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
