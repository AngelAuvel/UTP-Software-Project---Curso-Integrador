import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../modelos/usuario';
import { Producto } from '../modelos/producto';
import { Pedido, EstadoPedido } from '../modelos/pedido';
import { Proveedor } from '../modelos/proveedor';
import { Area } from '../modelos/area';
import { Categoria } from '../modelos/categoria';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = 'http://localhost:8080'; // URL de tu backend Spring Boot

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // Auth
  login(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/login`, credentials);
  }

  register(userData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/register`, userData, { headers: this.getAuthHeaders() });
  }

  changeInitialPassword(newPassword: string): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/auth/change-password`, { newPassword }, { headers: this.getAuthHeaders() });
  }

  // User Profile
  getCurrentUser(): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseUrl}/api/users/me`, { headers: this.getAuthHeaders() });
  }

  updateCurrentUser(userData: Partial<Usuario>): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/api/users/me`, userData, { headers: this.getAuthHeaders() });
  }

  changeProfilePassword(newPassword: string): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/api/users/me/change-password`, { newPassword }, { headers: this.getAuthHeaders() });
  }

  // Admin: Users
  getUsers(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/api/users`, { headers: this.getAuthHeaders() });
  }

  updateUser(id: number, userData: Partial<Usuario>): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/api/users/${id}`, userData, { headers: this.getAuthHeaders() });
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/api/users/${id}`, { headers: this.getAuthHeaders() });
  }

  // Products
  getProducts(): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.baseUrl}/api/products`, { headers: this.getAuthHeaders() });
  }

  getProductById(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.baseUrl}/api/products/${id}`, { headers: this.getAuthHeaders() });
  }

  createProduct(productData: any): Observable<Producto> {
    return this.http.post<Producto>(`${this.baseUrl}/api/products`, productData, { headers: this.getAuthHeaders() });
  }

  updateProduct(id: number, productData: any): Observable<Producto> {
    return this.http.put<Producto>(`${this.baseUrl}/api/products/${id}`, productData, { headers: this.getAuthHeaders() });
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/api/products/${id}`, { headers: this.getAuthHeaders() });
  }

  // Product Requests (Pedidos)
  createProductRequest(requestData: any): Observable<Pedido> {
    return this.http.post<Pedido>(`${this.baseUrl}/api/product-requests`, requestData, { headers: this.getAuthHeaders() });
  }

  getProductRequests(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(`${this.baseUrl}/api/product-requests`, { headers: this.getAuthHeaders() });
  }

  updateProductRequestStatus(id: number, estado: EstadoPedido): Observable<Pedido> {
    return this.http.put<Pedido>(`${this.baseUrl}/api/product-requests/${id}/status?estado=${estado}`, {}, { headers: this.getAuthHeaders() });
  }

  // Providers
  getProveedores(): Observable<Proveedor[]> {
    return this.http.get<Proveedor[]>(`${this.baseUrl}/api/proveedores`, { headers: this.getAuthHeaders() });
  }

  getProveedorById(id: number): Observable<Proveedor> {
    return this.http.get<Proveedor>(`${this.baseUrl}/api/proveedores/${id}`, { headers: this.getAuthHeaders() });
  }

  createProveedor(proveedorData: any): Observable<Proveedor> {
    return this.http.post<Proveedor>(`${this.baseUrl}/api/proveedores`, proveedorData, { headers: this.getAuthHeaders() });
  }

  updateProveedor(id: number, proveedorData: any): Observable<Proveedor> {
    return this.http.put<Proveedor>(`${this.baseUrl}/api/proveedores/${id}`, proveedorData, { headers: this.getAuthHeaders() });
  }

  deleteProveedor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/api/proveedores/${id}`, { headers: this.getAuthHeaders() });
  }

  // Statistics
  getStatistics(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/api/statistics`, { headers: this.getAuthHeaders() });
  }

  // Catalogos
  getAreas(): Observable<Area[]> {
    return this.http.get<Area[]>(`${this.baseUrl}/api/catalogs/areas`, { headers: this.getAuthHeaders() });
  }

  getCategories(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.baseUrl}/api/catalogs/categories`, { headers: this.getAuthHeaders() });
  }

  createCategory(categoryData: any): Observable<Categoria> {
    return this.http.post<Categoria>(`${this.baseUrl}/api/categories`, categoryData, { headers: this.getAuthHeaders() });
  }

  updateCategory(id: number, categoryData: any): Observable<Categoria> {
    return this.http.put<Categoria>(`${this.baseUrl}/api/categories/${id}`, categoryData, { headers: this.getAuthHeaders() });
  }

  deleteCategory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/api/categories/${id}`, { headers: this.getAuthHeaders() });
  }
}
