package com.utp.integrador.clinica.config;

import com.utp.integrador.clinica.model.Categoria;
import com.utp.integrador.clinica.model.Departamento;
import com.utp.integrador.clinica.model.Rol;
import com.utp.integrador.clinica.model.Usuario;
import com.utp.integrador.clinica.repository.CategoriaRepository;
import com.utp.integrador.clinica.repository.DepartamentoRepository;
import com.utp.integrador.clinica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Solo inicializar si no hay datos
        if (categoriaRepository.count() == 0) {
            initializeCategorias();
        }
        if (departamentoRepository.count() == 0) {
            initializeDepartamentos();
        }
        if (usuarioRepository.count() == 0) {
            initializeUsuarios();
        }
    }

    private void initializeCategorias() {
        List<Categoria> categorias = Arrays.asList(
            createCategoria("Material de Oficina", "Papelería y útiles de escritorio", "#3498db", "fa-clipboard"),
            createCategoria("Equipos de Cómputo", "Computadoras, impresoras y accesorios", "#e74c3c", "fa-laptop"),
            createCategoria("Mobiliario", "Muebles y equipamiento de oficina", "#f39c12", "fa-chair"),
            createCategoria("Consumibles", "Tóner, tintas y materiales de consumo", "#2ecc71", "fa-print"),
            createCategoria("Material de Limpieza", "Productos de aseo y desinfección", "#9b59b6", "fa-broom")
        );
        categoriaRepository.saveAll(categorias);
    }

    private void initializeDepartamentos() {
        List<Departamento> departamentos = Arrays.asList(
            createDepartamento("Administración", "ADM", "Departamento administrativo central", 5000.00),
            createDepartamento("Finanzas", "FIN", "Departamento de contabilidad y finanzas", 3000.00),
            createDepartamento("Recursos Humanos", "RRHH", "Gestión de personal", 2000.00),
            createDepartamento("Logística", "LOG", "Área de logística y almacén", 8000.00),
            createDepartamento("Servicios Generales", "SGEN", "Mantenimiento y servicios", 4000.00)
        );
        departamentoRepository.saveAll(departamentos);
    }

    private void initializeUsuarios() {
        // El departamento de Logística debe existir
        Departamento logistica = departamentoRepository.findByCodigo("LOG").orElse(null);

        Usuario admin = new Usuario();
        admin.setNombre("Administrador");
        admin.setApellido("Sistema");
        admin.setEmail("admin@clinicasjb.pe");
        admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña hasheada
        admin.setDepartamento(logistica);
        admin.setPuesto("Jefe de Logística");
        admin.setRol(Rol.ADMIN_LOGISTICA);
        admin.setEstado("Activo");
        // ultimo_acceso es NULL por defecto

        usuarioRepository.save(admin);
    }

    private Categoria createCategoria(String nombre, String descripcion, String color, String icono) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        categoria.setColorIdentificador(color);
        categoria.setIcono(icono);
        return categoria;
    }

    private Departamento createDepartamento(String nombre, String codigo, String descripcion, Double presupuesto) {
        Departamento depto = new Departamento();
        depto.setNombre(nombre);
        depto.setCodigo(codigo);
        depto.setDescripcion(descripcion);
        depto.setPresupuestoMensual(java.math.BigDecimal.valueOf(presupuesto));
        return depto;
    }
}
