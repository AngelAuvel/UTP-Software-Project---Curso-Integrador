import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pedido } from '../modelos/pedido.model'; // Importamos el modelo Pedido

@Injectable({
  providedIn: 'root'
})
export class PedidoServicio {
  // Ajustamos la URL base para el endpoint de pedidos
  private urlBase = 'http://localhost:8080/inventario-app/pedidos';

  // Inyectamos HttpClient para las peticiones HTTP
  constructor(private clienteHttp: HttpClient) { }

  /**
   * Obtiene la lista completa de pedidos.
   * @returns Un Observable que emite un array de objetos Pedido.
   */
  obtenerPedidos(): Observable<Pedido[]> {
    return this.clienteHttp.get<Pedido[]>(this.urlBase);
  }

  /**
   * Envía un nuevo pedido a la API para su registro.
   * @param pedido El objeto Pedido a agregar.
   * @returns Un Observable que emite una respuesta genérica (Object).
   */
  agregarPedido(pedido: Pedido): Observable<Object> {
    console.log("Pedido a enviar al servicio:", pedido);
    return this.clienteHttp.post(this.urlBase, pedido);
  }

  /**
   * Obtiene los detalles de un pedido específico por su ID.
   * @param id El ID del pedido.
   * @returns Un Observable que emite el objeto Pedido.
   */
  obtenerPedidoPorId(id: number): Observable<Pedido> {
    // URL: http://localhost:8080/inventario-app/pedidos/123
    return this.clienteHttp.get<Pedido>(`${this.urlBase}/${id}`);
  }

  /**
   * Actualiza un pedido existente.
   * @param id El ID del pedido a editar.
   * @param pedido El objeto Pedido con los datos actualizados.
   * @returns Un Observable que emite una respuesta genérica (Object).
   */
  editarPedido(id: number, pedido: Pedido): Observable<Object> {
    return this.clienteHttp.put(`${this.urlBase}/${id}`, pedido);
  }

  /**
   * Elimina un pedido por su ID.
   * @param id El ID del pedido a eliminar.
   * @returns Un Observable que emite una respuesta genérica (Object).
   */
  eliminarPedido(id: number): Observable<Object> {
    return this.clienteHttp.delete(`${this.urlBase}/${id}`);
  }

}