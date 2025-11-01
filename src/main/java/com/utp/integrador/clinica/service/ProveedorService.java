package com.utp.integrador.clinica.service;

import com.utp.integrador.clinica.model.Proveedor;
import java.util.List;

public interface ProveedorService {

    List<Proveedor> getAllProveedores();

    Proveedor getProveedorById(Integer id);

    Proveedor saveProveedor(Proveedor proveedor);

    void deleteProveedor(Integer id);
}
