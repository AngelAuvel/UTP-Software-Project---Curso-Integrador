import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Articulo } from '../modelos/articulo.model';

@Injectable({
  providedIn: 'root'
})
export class ArticuloServicio {
  // URL base de tu API, ajusta según sea necesario.
  // Asumo que tienes una ruta para Artículos/Catálogo
  private apiUrl = 'http://localhost:8080/inventario-app/articulos'; 

  private http = inject(HttpClient);

  constructor() { }

  /**
   * Obtiene la lista completa de artículos del catálogo.
   * @returns Observable<Articulo[]>
   */
  listarArticulos(): Observable<Articulo[]> {
    return this.http.get<Articulo[]>(this.apiUrl)
      .pipe(
        catchError(error => {
          console.error('Error al listar artículos:', error);
          // Si el API falla, devolvemos un array vacío o un error
          // En un entorno real, manejarías el error de forma más robusta.
          return throwError(() => new Error('Error al cargar el catálogo de artículos.'));
        })
      );
  }

  // Aquí podrías agregar métodos para agregar, editar o eliminar artículos si fuera necesario.
}
