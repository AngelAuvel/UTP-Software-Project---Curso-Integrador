import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Estado } from '../modelos/estado.model';

@Injectable({
  providedIn: 'root'
})
export class EstadoServicio {
  // URL base para el endpoint de áreas en tu API
  private urlBase = 'http://localhost:8080/inventario-app/estados';

  // Inyecta HttpClient
  constructor(private clienteHttp: HttpClient) { }

  /**
   * Obtiene la lista completa de áreas (catálogo).
   * @returns Un Observable que emite un array de objetos Area.
   */
  listarEstado(): Observable<Estado[]> {
    return this.clienteHttp.get<Estado[]>(this.urlBase);
  }
}