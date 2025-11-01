import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Producto } from '../../modelos/producto';
import { Page } from '../../modelos/page';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private apiUrl = `${environment.apiUrl}/productos`;

  constructor(private http: HttpClient) { }

  getProductos(page: number, size: number, query: string = ''): Observable<Page<Producto>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (query) {
      params = params.set('query', query);
    }

    return this.http.get<Page<Producto>>(this.apiUrl, { params });
  }

  getAllProductos(): Observable<Producto[]> {
    // Este endpoint debe devolver todos los productos sin paginar.
    // Asumimos que el backend lo soporta en una ruta diferente o con parámetros.
    // Por simplicidad, llamaremos a la paginación con un tamaño muy grande.
    return this.http.get<Producto[]>(`${this.apiUrl}/all`);
  }

  getProducto(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`);
  }

  crearProducto(producto: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, producto);
  }

  actualizarProducto(id: number, producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${id}`, producto);
  }

  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
