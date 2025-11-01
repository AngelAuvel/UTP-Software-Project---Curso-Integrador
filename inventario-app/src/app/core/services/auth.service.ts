import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';
import { environment } from '../../../environments/environment';
import { LoginRequest } from '../../modelos/login-request';
import { JwtAuthenticationResponse } from '../../modelos/jwt-authentication-response';
import { Rol } from '../../modelos/usuario';

interface DecodedToken {
  sub: string;
  iat: number;
  exp: number;
  roles: string[]; // El backend devuelve strings con el prefijo ROLE_
  departamentoId?: number; // Asumiendo que el backend incluye esto en el token
  departamentoNombre?: string; // Asumiendo que el backend incluye esto en el token
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY = 'accessToken';
  private _userRoles = signal<Rol[]>([]);
  private _userDepartamentoId = signal<number | null>(null);
  private _userDepartamentoNombre = signal<string | null>(null);

  public userRoles = this._userRoles.asReadonly();
  public userDepartamentoId = this._userDepartamentoId.asReadonly();
  public userDepartamentoNombre = this._userDepartamentoNombre.asReadonly();

  constructor(private http: HttpClient) {
    this.loadUserRolesAndDepartment();
  }

  private loadUserRolesAndDepartment(): void {
    const token = this.getToken();
    if (token) {
      try {
        const decodedToken: DecodedToken = jwtDecode(token);
        const rolesWithoutPrefix = (decodedToken.roles || []).map(role => role.replace('ROLE_', '') as Rol);
        this._userRoles.set(rolesWithoutPrefix);
        this._userDepartamentoId.set(decodedToken.departamentoId || null);
        this._userDepartamentoNombre.set(decodedToken.departamentoNombre || null);
      } catch (error) {
        console.error("Error decodificando el token", error);
        this.logout();
      }
    }
  }

  login(loginRequest: LoginRequest): Observable<JwtAuthenticationResponse> {
    return this.http.post<JwtAuthenticationResponse>(`${environment.apiUrl}/auth/signin`, loginRequest)
      .pipe(
        tap(response => {
          if (response && response.accessToken) {
            localStorage.setItem(this.TOKEN_KEY, response.accessToken);
            this.loadUserRolesAndDepartment(); // Cargar roles y departamento después del login
          }
        })
      );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    this._userRoles.set([]);
    this._userDepartamentoId.set(null);
    this._userDepartamentoNombre.set(null);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  hasRole(role: Rol): boolean {
    return this.userRoles().includes(role);
  }
}
