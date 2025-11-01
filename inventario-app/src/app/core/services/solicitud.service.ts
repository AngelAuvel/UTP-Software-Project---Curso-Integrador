import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Solicitud } from '../../modelos/solicitud';

@Injectable({
  providedIn: 'root'
})
export class SolicitudService {

  private apiUrl = `${environment.apiUrl}/solicitudes`;

  constructor(private http: HttpClient) { }

  getSolicitudes(): Observable<Solicitud[]> {
    return this.http.get<Solicitud[]>(this.apiUrl);
  }

  getSolicitud(id: number): Observable<Solicitud> {
    return this.http.get<Solicitud>(`${this.apiUrl}/${id}`);
  }

  crearSolicitud(solicitud: Solicitud): Observable<Solicitud> {
    return this.http.post<Solicitud>(this.apiUrl, solicitud);
  }

  actualizarSolicitud(id: number, solicitud: Solicitud): Observable<Solicitud> {
    return this.http.put<Solicitud>(`${this.apiUrl}/${id}`, solicitud);
  }

  eliminarSolicitud(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  aprobarSolicitud(id: number): Observable<Solicitud> {
    return this.http.post<Solicitud>(`${this.apiUrl}/${id}/aprobar`, {});
  }

  rechazarSolicitud(id: number, motivo: string): Observable<Solicitud> {
    return this.http.post<Solicitud>(`${this.apiUrl}/${id}/rechazar`, { motivo });
  }

  despacharSolicitud(id: number): Observable<Solicitud> {
    return this.http.post<Solicitud>(`${this.apiUrl}/${id}/despachar`, {});
  }

  recibirSolicitud(id: number): Observable<Solicitud> {
    return this.http.post<Solicitud>(`${this.apiUrl}/${id}/recibir`, {});
  }
}
