import { Component, inject } from '@angular/core';
import { RouterOutlet, Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs';
import { CommonModule } from '@angular/common'; // 👈 Necesario para usar *ngIf
import { Auth } from './servicios/auth';

@Component({
  selector: 'app-root',
  standalone: true,
  // 👈 AGREGAMOS CommonModule AQUÍ
  imports: [RouterOutlet, CommonModule], 
  templateUrl: './app.html'
})
export class App {
  title = 'inventario-app';
  
  // Propiedad para controlar la visibilidad del Navbar
  mostrarNavbar: boolean = false; 

  // Inyección del Router
  private router = inject(Router);
  private auth = inject(Auth); 

  constructor() {
    // Escuchar los eventos del Router para saber cuándo cambia la ruta
    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd)
      )
      .subscribe((event: NavigationEnd) => {
        // La clave es verificar si la URL incluye '/login'
        this.mostrarNavbar = !event.urlAfterRedirects.includes('/login');
      });
  }

  // MÉTODO PARA CERRAR SESIÓN (Llamado desde el Navbar)
  logout() {
    // 1. Llama al método logout de tu servicio de autenticación (Auth)
    this.auth.logout(); // 👈 CAMBIADO: Usando 'this.auth'
    
    // 2. Redirige al usuario a la página de login
    this.router.navigate(['/login']); 
  }
}
