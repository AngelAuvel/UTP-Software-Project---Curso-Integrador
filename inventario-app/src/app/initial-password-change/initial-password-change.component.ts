import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../servicios/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-initial-password-change',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './initial-password-change.component.html',
})
export class InitialPasswordChangeComponent {
  newPassword = '';
  confirmPassword = '';
  errorMessage: string | null = null;
  loading = false;

  private apiService = inject(ApiService);
  private router = inject(Router);

  changePassword() {
    this.errorMessage = null;
    if (this.newPassword !== this.confirmPassword) {
      this.errorMessage = 'Las contraseñas no coinciden.';
      return;
    }

    if (!this.newPassword || this.newPassword.length < 6) { // Ejemplo de validación simple
      this.errorMessage = 'La contraseña debe tener al menos 6 caracteres.';
      return;
    }

    this.loading = true;
    this.apiService.changeInitialPassword(this.newPassword).subscribe({
      next: () => {
        // Contraseña cambiada exitosamente, redirigir al dashboard
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error('Error al cambiar la contraseña inicial:', err);
        this.errorMessage = 'Error al cambiar la contraseña. Inténtalo de nuevo.';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}
