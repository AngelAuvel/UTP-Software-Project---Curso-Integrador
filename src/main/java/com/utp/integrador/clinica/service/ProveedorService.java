package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.dto.ProveedorDto;
import com.utp.integrador.clinica.model.entities.Proveedor;
import com.utp.integrador.clinica.model.repository.ProveedorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Transactional(readOnly = true)
    public List<ProveedorDto> findAll() {
        return proveedorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProveedorDto findById(Long id) {
        return proveedorRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Transactional
    public ProveedorDto save(ProveedorDto proveedorDto) {
        Proveedor proveedor = convertToEntity(proveedorDto);
        proveedor.setEstado("ACTIVO");
        Proveedor savedProveedor = proveedorRepository.save(proveedor);
        return convertToDto(savedProveedor);
    }

    @Transactional
    public ProveedorDto update(Long id, ProveedorDto proveedorDto) {
        Proveedor proveedor = proveedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        
        proveedor.setNombre(proveedorDto.nombre());
        proveedor.setRuc(proveedorDto.ruc());
        proveedor.setTelefono(proveedorDto.telefono());
        proveedor.setDireccion(proveedorDto.direccion());
        proveedor.setEstado(proveedorDto.estado());

        Proveedor updatedProveedor = proveedorRepository.save(proveedor);
        return convertToDto(updatedProveedor);
    }

    @Transactional
    public void delete(Long id) {
        proveedorRepository.deleteById(id);
    }

    private ProveedorDto convertToDto(Proveedor proveedor) {
        return new ProveedorDto(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getRuc(),
                proveedor.getTelefono(),
                proveedor.getDireccion(),
                proveedor.getEstado()
        );
    }

    private Proveedor convertToEntity(ProveedorDto proveedorDto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(proveedorDto.nombre());
        proveedor.setRuc(proveedorDto.ruc());
        proveedor.setTelefono(proveedorDto.telefono());
        proveedor.setDireccion(proveedorDto.direccion());
        proveedor.setEstado(proveedorDto.estado());
        
        return proveedor;
    }
}
