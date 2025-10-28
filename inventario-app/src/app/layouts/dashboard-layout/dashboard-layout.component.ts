import { Component, inject } from '@angular/core';
import { Router, RouterOutlet, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../servicios/api.service';
import { Usuario } from '../../modelos/usuario';

@Component({
  selector: 'app-dashboard-layout',
  standalone: true,
  imports: [RouterOutlet, CommonModule, RouterModule],
  template: `
    <div class=\"flex h-screen bg-gray-100\">
      <!-- Sidebar -->
      <aside class=\"w-64 bg-gray-800 text-white flex flex-col\">
        <div class=\"bg-gray-900 text-white text-xl font-bold p-4\">Clínica SJB</div>
        <nav class=\"flex-grow p-4 space-y-2\">
          <a routerLink=\"/home\" routerLinkActive=\"bg-gray-700\" class=\"block px-4 py-2 rounded-lg hover:bg-gray-700\">Dashboard</a>
          <a routerLink=\"/productos\" routerLinkActive=\"bg-gray-700\" class=\"block px-4 py-2 rounded-lg hover:bg-gray-700\">Productos</a>
          <a routerLink=\"/proveedores\" routerLinkActive=\"bg-gray-700\" class=\"block px-4 py-2 rounded-lg hover:bg-gray-700\">Proveedores</a>
          <a routerLink=\"/categorias\" routerLinkActive=\"bg-gray-700\" class=\"block px-4 py-2 rounded-lg hover:bg-gray-700\">Categorías</a>
          <a *ngIf=\"currentUser?.rol === 'ADMIN'\" routerLink=\"/usuarios\" routerLinkActive=\"bg-gray-700\" class=\"block px-4 py-2 rounded-lg hover:bg-gray-700\">Usuarios</a>
          <a *ngIf=\"currentUser?.rol === 'ADMIN'\" routerLink=\"/areas\" routerLinkActive=\"bg-gray-700\" class=\"block px-4 py-2 rounded-lg hover:bg-gray-700\">Áreas</a>
        </nav>
        <div class=\"p-4 border-t border-gray-700\">
          <div *ngIf=\"currentUser\" class=\"mb-4\">
            <p class=\"text-sm font-medium\">{{ currentUser.nombres ?? '' }} {{ currentUser.apellidos ?? '' }}</p>
            <p class=\"text-xs text-gray-400\">{{ currentUser.rol ?? '' }}</p>
          </div>
          <a routerLink=\"/perfil\" class=\"block text-center w-full px-4 py-2 rounded-lg bg-gray-700 hover:bg-gray-600 mb-2\">Mi Perfil</a>
          <button (click)=\"logout()\" class=\"w-full px-4 py-2 rounded-lg bg-red-600 hover:bg-red-700\">
            Cerrar Sesión
          </button>
        </div>
      </aside>

      <!-- Contenido Principal -->
      <main class=\"flex-1 overflow-y-auto\">
        <router-outlet></router-outlet>
      </main>
    </div>
  `
})
export class DashboardLayoutComponent {
  currentUser: Usuario | null = null;

  private router = inject(Router);
  private apiService = inject(ApiService);

  constructor() {
    this.loadCurrentUser();
  }

  loadCurrentUser() {
    const token = localStorage.getItem('token');
    if (token) {
      this.apiService.getCurrentUser().subscribe({
        next: (user: Usuario) => this.currentUser = user,
        error: () => this.logout()
      });
    }
  }

  logout() {
    localStorage.removeItem('token');
    this.currentUser = null;
    this.router.navigate(['/login']);
  }
}
