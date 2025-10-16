import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../modelos/usuario.model'; // Asegúrate de que esta ruta sea correcta

@Injectable({
  providedIn: 'root'
})
export class Auth {
  
  // URL base de tu backend Spring Boot (el path de tu controlador principal)
  private urlBase = 'http://localhost:8080/inventario-app';

  // 1. Constructor: Inyectamos el HttpClient
  constructor(private clienteHttp: HttpClient) { }

  /**
   * Realiza la solicitud de logeo al endpoint /login del backend.
   * @param nombre El nombre de usuario ingresado.
   * @param clave La clave de acceso ingresada.
   * @returns Un Observable con el objeto Usuario si el logeo es exitoso.
   */
  login(nombre: string, clave: string): Observable<Usuario> {
    
    // Objeto con las credenciales que se enviarán en el cuerpo (Body) del POST
    const loginPayload: Partial<Usuario> = {
      nombre: nombre, 
      clave: clave
    };

    // Llama al endpoint POST: http://localhost:8080/inventario-app/login
    // El backend espera el objeto {nombre, clave} y devuelve un objeto Usuario
    return this.clienteHttp.post<Usuario>(`${this.urlBase}/login`, loginPayload);
  }

  // --- Funciones Adicionales Esenciales ---

  /**
   * Cierra la sesión del usuario (limpia el token/sesión almacenada en el navegador).
   */
  logout(): void {
    // En un sistema con tokens JWT, aquí es donde se elimina el token 
    // almacenado (ej. en localStorage) para invalidar la sesión del cliente.
    localStorage.removeItem('jwt_token'); 
    // Puedes agregar más lógica de limpieza si es necesario.
    console.log('Sesión cerrada y token eliminado.');
  }

  /**
   * Verifica si el usuario está actualmente autenticado.
   * @returns boolean
   */
  isAuthenticated(): boolean {
    // La forma más simple es verificar si existe un token de sesión.
    // Esto es vital para proteger las rutas de tu aplicación Angular.
    return !!localStorage.getItem('jwt_token');
  }
}