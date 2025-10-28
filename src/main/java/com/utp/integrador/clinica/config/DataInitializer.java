package com.utp.integrador.clinica.config;

import com.utp.integrador.clinica.model.entities.Area;
import com.utp.integrador.clinica.model.entities.Categoria;
import com.utp.integrador.clinica.model.entities.Rol;
import com.utp.integrador.clinica.model.entities.Usuario;
import com.utp.integrador.clinica.model.repository.AreaRepository;
import com.utp.integrador.clinica.model.repository.CategoriaRepository;
import com.utp.integrador.clinica.model.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final AreaRepository areaRepository;
    private final CategoriaRepository categoriaRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, AreaRepository areaRepository, CategoriaRepository categoriaRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.areaRepository = areaRepository;
        this.categoriaRepository = categoriaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            // Crear usuario admin
            Usuario admin = new Usuario();
            admin.setNombres("Administrador");
            admin.setApellidos("Sistema");
            admin.setDni("00000000");
            admin.setCorreo("admin@clinicasjb.com");
            admin.setPassword(passwordEncoder.encode("Admin123!"));
            admin.setRol(Rol.ADMIN);
            admin.setArea("Administración");
            admin.setFechaRegistro(LocalDateTime.now());
            admin.setUltimoAcceso(LocalDateTime.now());
            admin.setDebeCambiarContraseña(true);
            admin.setEstado("ACTIVO");
            usuarioRepository.save(admin);
        }

        if (areaRepository.count() == 0) {
            // Inicializar áreas
            List<Area> areas = List.of(
                new Area("Administración"),
                new Area("Urgencias"),
                new Area("Hospitalización"),
                new Area("Quirófano"),
                new Area("Laboratorio"),
                new Area("Farmacia"),
                new Area("Imagenología")
            );
            areaRepository.saveAll(areas);
        }

        if (categoriaRepository.count() == 0) {
            // Inicializar categorías
            List<Categoria> categorias = List.of(
                new Categoria("Material Médico"),
                new Categoria("Equipamiento"),
                new Categoria("Medicamentos"),
                new Categoria("Material de Oficina"),
                new Categoria("Limpieza"),
                new Categoria("Otros")
            );
            categoriaRepository.saveAll(categorias);
        }
    }
}
