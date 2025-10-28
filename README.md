# Sistema de Gestión Logística - Clínica San Juan Bautista

Este proyecto es una aplicación web completa para la gestión de inventario y logística del área administrativa de la Clínica San Juan Bautista.

## 📜 Descripción

El sistema permite a los usuarios autenticados gestionar productos, proveedores, y solicitudes de inventario. Cuenta con un sistema de roles (ADMIN, LOGISTICA, USUARIO) que define los permisos para cada acción dentro de la aplicación. El flujo principal incluye la autenticación de usuarios, un cambio de contraseña obligatorio en el primer inicio de sesión, y un dashboard con estadísticas clave para la toma de decisiones.

## ✨ Características Principales

- **Autenticación y Seguridad:** Sistema de login robusto con JSON Web Tokens (JWT).
- **Gestión de Usuarios:** CRUD completo para la administración de usuarios y roles.
- **Gestión de Inventario:** Administración de productos, categorías y proveedores.
- **Flujo de Solicitudes:** Los usuarios pueden solicitar productos, y el área de logística puede aprobar o rechazar dichas solicitudes, afectando el stock en tiempo real.
- **Trazabilidad:** Registro de todos los movimientos de inventario (entradas y salidas).
- **Dashboard de Estadísticas:** Visualización de métricas clave para el rol de Administrador.
- **Gestión de Perfil:** Los usuarios pueden actualizar su información de contacto y contraseña.

---

## 🛠️ Tecnologías Utilizadas

El proyecto sigue una arquitectura de aplicación de página única (SPA) con un backend RESTful.

### **Backend**

- **Lenguaje:** Java 17
- **Framework:** Spring Boot 3.3
- **Seguridad:** Spring Security (Autenticación con JWT)
- **Base de Datos:** Spring Data JPA con Hibernate
- **Servidor:** Apache Tomcat (embebido)
- **Base de Datos:** MySQL

### **Frontend**

- **Framework:** Angular 20
- **Estilos:** Tailwind CSS
- **Llamadas HTTP:** Angular HttpClient
- **Gestión de Rutas:** Angular Router

---

## 🚀 Puesta en Marcha

Para ejecutar el proyecto en un entorno de desarrollo local, sigue estos pasos.

### **Requisitos Previos**

- JDK 17 o superior (ej. OpenJDK).
- Maven 3.8 o superior.
- Node.js 20 o superior (incluye npm).
- Una instancia de MySQL en ejecución.

### **1. Configuración del Backend (Spring Boot)**

1.  **Base de Datos:**
    - Crea una base de datos en MySQL con el nombre `clinicadb`.
    - Abre el archivo `src/main/resources/application.properties`.
    - Modifica las propiedades `spring.datasource.username` y `spring.datasource.password` con tus credenciales de MySQL.

2.  **Ejecutar la Aplicación:**
    - Navega a la raíz del proyecto (`UTP-Software-Project---Curso-Integrador`).
    - Ejecuta el siguiente comando en tu terminal:
      ```bash
      ./mvnw spring-boot:run
      ```
    - El servidor backend se iniciará en `http://localhost:8080`.

### **2. Configuración del Frontend (Angular)**

1.  **Instalar Dependencias:**
    - Abre una nueva terminal.
    - Navega a la carpeta del frontend: `cd inventario-app`.
    - Instala todas las dependencias necesarias:
      ```bash
      npm install
      ```

2.  **Ejecutar la Aplicación:**
    - En la misma terminal, ejecuta el siguiente comando:
      ```bash
      ng serve -o
      ```
    - Esto iniciará el servidor de desarrollo de Angular y abrirá automáticamente tu navegador en `http://localhost:4200/`.

---

## 🔑 Credenciales de Acceso

Al iniciar la aplicación por primera vez, se creará un usuario administrador por defecto. Utiliza las siguientes credenciales para acceder:

- **Correo:** `admin@clinicasjb.com`
- **Contraseña:** `Admin123!`

**Nota:** Se te pedirá que cambies la contraseña de forma obligatoria después del primer inicio de sesión.

---

## 📄 API Endpoints Principales

A continuación se describen las rutas base de la API. Todos los endpoints bajo `/api` requieren un token de autenticación.

- `POST /auth/login`: Iniciar sesión.
- `POST /auth/register`: Registrar un nuevo usuario (protegido).
- `POST /auth/change-password`: Cambio de contraseña obligatorio inicial.

- `GET, PUT /api/users/me`: Obtener o actualizar el perfil del usuario actual.
- `POST /api/users/me/change-password`: Cambio de contraseña voluntario.

- `GET, POST, PUT, DELETE /api/users`: CRUD de usuarios (Admin).
- `GET, POST, PUT, DELETE /api/products`: CRUD de productos.
- `GET, POST, PUT /api/product-requests`: Gestión de solicitudes de productos.
- `GET, POST, PUT, DELETE /api/providers`: CRUD de proveedores.
- `GET /api/statistics`: Obtener estadísticas para el dashboard (Admin).
- `GET, POST /api/catalogs/...`: Obtener catálogos de áreas y categorías.
