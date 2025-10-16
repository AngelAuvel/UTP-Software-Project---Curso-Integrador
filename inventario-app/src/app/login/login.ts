import { Component, inject } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Usuario } from '../modelos/usuario.model'; 
// Asumiendo que el servicio se llama 'Auth' como indicaste
import { Auth } from '../servicios/auth'; 
import { CommonModule } from '@angular/common'; // 👈 IMPORTACIÓN NECESARIA

@Component({
  selector: 'app-login',
  standalone: true,
  // AÑADIDO CommonModule para que *ngIf funcione en el template (login.html)
  imports: [FormsModule, CommonModule], 
  // Asegúrate de que el templateURL sea 'login.html'
  templateUrl: './login.html',
})
export class Login { 

  // Asumimos Solución 2 para el error de tipo anterior
  usuario: Usuario = { idUsuario: 0, nombre: '', clave: '' }; 

  mensajeError: string | null = null; 

  // Usando el servicio 'Auth'
  private authService = inject(Auth);
  private router = inject(Router);

  constructor() { }

  onInputChange() {
    this.mensajeError = null;
  }

  login(form: NgForm) {
    this.mensajeError = null; // Limpiar cualquier error anterior
    
    if (form.invalid) {
      this.mensajeError = 'Por favor, introduce tu correo y contraseña.';
      return;
    }

    this.authService.login(this.usuario.nombre, this.usuario.clave).subscribe({
      next: (response) => {
        console.log('Login exitoso!', response);
        this.router.navigate(['/productos']); 
      },
      error: (err) => {
        console.error('Login fallido:', err);
        // REEMPLAZO DEL ALERT() por el manejo de la variable de error
        this.mensajeError = 'Credenciales inválidas. Por favor, intenta de nuevo.';
        this.usuario.clave = '';
      }
    });
  }
}
