import { Component, inject } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ApiService } from '../servicios/api.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
})
export class Login {
  credentials = { correo: '', password: '' };
  loading = false;
  mensajeError: string | null = null;

  private apiService = inject(ApiService);
  private router = inject(Router);

  constructor() { }

  onInputChange() {
    this.mensajeError = null;
  }

  login(form: NgForm) {
    this.mensajeError = null;
    if (form.invalid) {
      this.mensajeError = 'Por favor, introduce tu correo y contraseña.';
      return;
    }

    this.loading = true;
    this.apiService.login({ username: this.credentials.correo, password: this.credentials.password }).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        if (response.debeCambiarContraseña) {
          this.router.navigate(['/initial-password-change']);
        } else {
          this.router.navigate(['/home']);
        }
      },
      error: (err) => {
        this.mensajeError = 'Credenciales inválidas. Por favor, intenta de nuevo.';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}
