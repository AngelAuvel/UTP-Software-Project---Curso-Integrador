import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Area } from '../modelos/area.model';

@Injectable({
  providedIn: 'root'
})
export class AreaServicio {
  // URL base para el endpoint de áreas en tu API
  private urlBase = 'http://localhost:8080/inventario-app/areas';

  // Inyecta HttpClient
  constructor(private clienteHttp: HttpClient) { }

  /**
   * Obtiene la lista completa de áreas (catálogo).
   * @returns Un Observable que emite un array de objetos Area.
   */
  listarArea(): Observable<Area[]> {
    return this.clienteHttp.get<Area[]>(this.urlBase);
  }
}