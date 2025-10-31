import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Usuario } from '../modelos/usuario.model';

// Definición de la respuesta esperada del backend (debe contener el token y el usuario)
interface LoginResponse {
  usuario: Usuario; 
  token: string;   
}

@Injectable({
  providedIn: 'root'
})
export class Auth {
  
  private urlBase = 'http://localhost:8080/inventario-app';
  // Variable para almacenar el ID en memoria (opcional, pero útil)
  private userLoggedInId: number | null = null; 

  constructor(private clienteHttp: HttpClient) { 
     // Intentamos recuperar el ID del usuario si existe en localStorage al inicio
     this.loadUserIdFromStorage();
  }

  login(nombre: string, clave: string): Observable<LoginResponse> {
    const loginPayload = { nombre, clave };

    // CLAVE: Asegúrate de que la URL sea correcta (usando /auth/login)
    return this.clienteHttp.post<LoginResponse>(`${this.urlBase}/login`, loginPayload).pipe(
      tap(response => {
        console.log('[AUTH] Respuesta recibida del backend:', response);
        if (response && response.token) {
          // Pasamos el usuario completo a la función de manejo
          this.handleSuccessfulLogin(response.token, response.usuario);
        } else {
            console.error('[AUTH] Error: Respuesta de Login incompleta. Faltan token o datos de usuario.');
        }
      })
    );
  }

  /**
   * Maneja el login exitoso: guarda el token Y el ID del usuario.
   * @param token El token JWT recibido del backend.
   * @param usuario El objeto usuario recibido del backend (debe contener id).
   */
  private handleSuccessfulLogin(token: string, usuario: Usuario): void {
    localStorage.setItem('jwt_token', token); 
    
    // CORRECCIÓN CLAVE: Volvemos a usar 'id' para coincidir con el modelo Usuario
    if (usuario.id) {
        // Guardar el ID del usuario en localStorage y memoria
        this.userLoggedInId = usuario.id;
        localStorage.setItem('user_id', String(usuario.id)); 
        console.log(`[AUTH] ID de usuario (${usuario.id}) guardado en localStorage.`);
    } else {
        console.error('[AUTH] ADVERTENCIA: Token guardado, pero ID (usuario.id) no encontrado en el objeto de usuario. Asegúrate de que tu backend lo envíe.');
    }
    
    console.log('Login exitoso. Token JWT e ID de usuario guardados.');
  }

  /**
   * Carga el ID del usuario desde localStorage al iniciar el servicio.
   */
  private loadUserIdFromStorage(): void {
    const id = localStorage.getItem('user_id');
    if (id) {
        this.userLoggedInId = Number(id);
        console.log(`[AUTH] ID de usuario cargado desde localStorage: ${this.userLoggedInId}`);
    } else {
        console.log('[AUTH] No hay ID de usuario en localStorage.');
    }
  }

  /**
   * Obtiene el ID del usuario actualmente logueado.
   * @returns El ID del usuario o null si no está logueado.
   */
  getUserId(): number | null {
    // Si ya lo tenemos en memoria, lo devolvemos
    if (this.userLoggedInId !== null) {
        return this.userLoggedInId;
    }
    
    // Si no, intentamos cargarlo del localStorage (esta función también actualiza userLoggedInId)
    this.loadUserIdFromStorage(); 
    return this.userLoggedInId;
  }

  /**
   * Obtiene el token JWT del almacenamiento local.
   * @returns El token JWT o null si no existe.
   */
  getToken(): string | null {
    return localStorage.getItem('jwt_token');
  }

  /**
   * Cierra la sesión del usuario (elimina el token y el ID almacenado).
   */
  logout(): void {
    localStorage.removeItem('jwt_token'); 
    localStorage.removeItem('user_id'); // CLAVE: Limpiar el ID
    this.userLoggedInId = null; 
    console.log('Sesión cerrada, token y ID eliminados.');
  }

  /**
   * Verifica si el usuario está actualmente autenticado revisando la existencia del token.
   * @returns boolean
   */
  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}
