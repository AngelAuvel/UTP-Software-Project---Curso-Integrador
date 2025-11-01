import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Usuario } from '../../modelos/usuario';
import { Page } from '../../modelos/page';
import { CambiarClaveRequest } from '../../modelos/cambiar-clave-request';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private apiUrl = `${environment.apiUrl}/usuarios`;

  constructor(private http: HttpClient) { }

  getUsuarios(page: number, size: number, query: string = ''): Observable<Page<Usuario>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (query) {
      params = params.set('query', query);
    }
    return this.http.get<Page<Usuario>>(this.apiUrl, { params });
  }

  getAllUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/all`);
  }

  getUsuario(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/${id}`);
  }

  getPerfilUsuario(): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/perfil`);
  }

  crearUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.apiUrl, usuario);
  }

  actualizarUsuario(id: number, usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrl}/${id}`, usuario);
  }

  eliminarUsuario(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  cambiarClave(request: CambiarClaveRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/cambiar-clave`, request);
  }
}
