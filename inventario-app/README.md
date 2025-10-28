# Inventario App (Frontend Angular)

Este directorio contiene el código fuente del proyecto frontend para el **Sistema de Gestión Logística**, desarrollado con Angular.

Para una visión general de la arquitectura completa del proyecto (incluyendo el backend), por favor consulta el [README principal del proyecto](../README.md).

---

## 🚀 Puesta en Marcha (Frontend)

Sigue estos pasos para ejecutar la aplicación de Angular en tu entorno local.

### Requisitos Previos

- Node.js (versión 20 o superior)
- npm (generalmente se instala con Node.js)

### 1. Instalación de Dependencias

Navega a este directorio (`inventario-app`) en tu terminal e instala las dependencias del proyecto:

```bash
npm install
```

### 2. Servidor de Desarrollo

Una vez instaladas las dependencias, ejecuta el siguiente comando para iniciar el servidor de desarrollo de Angular:

```bash
ng serve
```

Navega a `http://localhost:4200/` en tu navegador. La aplicación se recargará automáticamente si realizas cambios en los archivos de código fuente.

### ❗️ Importante: Conexión con el Backend

Esta aplicación frontend está diseñada para comunicarse con el backend de Spring Boot. Para que la aplicación funcione completamente (login, carga de datos, etc.), **el servidor backend debe estar en ejecución**.

Por defecto, la aplicación intentará conectarse a `http://localhost:8080`. Asegúrate de que el backend esté corriendo en esa dirección.

---

## ⚙️ Comandos Útiles de Angular CLI

- **Generar Componentes:**
  ```bash
  ng generate component nombre-del-componente
  ```

- **Generar Servicios:**
  ```bash
  ng generate service nombre-del-servicio
  ```

- **Construir para Producción (Build):**
  ```bash
  ng build
  ```
  Los artefactos de la compilación se almacenarán en el directorio `dist/`.

- **Ejecutar Pruebas Unitarias:**
  ```bash
  ng test
  ```

---

## 📂 Estructura del Proyecto

- `src/app/components/` o `src/app/[nombre-componente]/`: Contiene los componentes reutilizables de la aplicación.
- `src/app/modelos/`: Contiene las interfaces de TypeScript que definen la estructura de los datos (ej. `Usuario`, `Producto`).
- `src/app/servicios/`: Contiene los servicios de Angular, incluyendo `api.service.ts` que centraliza la comunicación con el backend.
- `src/app/app.routes.ts`: Define las rutas de navegación de la aplicación.
