package com.utp.integrador.clinica.controller;

import com.utp.integrador.clinica.dto.PedidoDto;
import com.utp.integrador.clinica.model.entities.EstadoPedido;
import com.utp.integrador.clinica.service.ProductRequestService;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product-requests")
public class ProductRequestController {

    private final ProductRequestService productRequestService;

    public ProductRequestController(ProductRequestService productRequestService) {
        this.productRequestService = productRequestService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<List<PedidoDto>> getAllPedidos() {
        return ResponseEntity.ok(productRequestService.findAll());
    }

    @PostMapping
    public ResponseEntity<PedidoDto> createPedido(@RequestBody PedidoDto pedidoDto, Principal principal) {
        return new ResponseEntity<>(productRequestService.createPedido(pedidoDto, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LOGISTICA')")
    public ResponseEntity<PedidoDto> updatePedidoStatus(
            @PathVariable Long id,
            @RequestParam("estado") EstadoPedido estado,
            Principal principal) {
        return ResponseEntity.ok(productRequestService.updatePedidoStatus(id, estado, principal.getName()));
    }
}
