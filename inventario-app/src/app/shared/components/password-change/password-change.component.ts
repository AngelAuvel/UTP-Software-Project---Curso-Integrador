import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuarioService } from '../../../core/services/usuario.service';
import { NotificationService } from '../../../core/services/notification.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-password-change',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './password-change.component.html',
  styleUrls: ['./password-change.component.css']
})
export class PasswordChangeComponent {
  private fb = inject(FormBuilder);
  private usuarioService = inject(UsuarioService);
  private notificationService = inject(NotificationService);
  private authService = inject(AuthService);

  passwordForm = this.fb.group({
    claveActual: ['', Validators.required],
    nuevaClave: ['', [Validators.required, Validators.minLength(6)]]
  });

  onSubmit(): void {
    if (this.passwordForm.invalid) {
      this.notificationService.showWarning('Por favor, complete todos los campos.');
      return;
    }

    const request = this.passwordForm.value;
    this.usuarioService.cambiarClave({ claveActual: request.claveActual!, nuevaClave: request.nuevaClave! })
      .subscribe({
        next: () => {
          this.notificationService.showSuccess('Contraseña cambiada exitosamente. Por favor, inicie sesión de nuevo.');
          this.authService.logout();
          // Aquí podríamos cerrar el modal y redirigir, pero eso lo manejará el interceptor.
          window.location.reload(); // Forzamos un refresco para que el interceptor actúe
        },
        error: (err) => {
          this.notificationService.showError('La contraseña actual es incorrecta.');
          console.error(err);
        }
      });
  }
}
