import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../servicios/api.service';
import { Usuario } from '../modelos/usuario';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './perfil.component.html',
})
export class PerfilComponent implements OnInit {
  private apiService = inject(ApiService);

  currentUser: Usuario | null = null;
  loading = true; // Para la carga inicial del perfil y actualización de datos
  showPasswordModal = false;

  // Campos editables
  telefono = '';
  direccion = '';

  // Campos para cambiar contraseña
  newPassword = '';
  confirmPassword = '';
  passwordChangeErrorMessage: string | null = null; // Mensaje de error específico para el cambio de contraseña
  passwordChangeLoading = false; // Estado de carga específico para el cambio de contraseña

  ngOnInit() {
    this.loadCurrentUser();
  }

  loadCurrentUser() {
    this.loading = true;
    this.apiService.getCurrentUser().subscribe({
      next: (user) => {
        this.currentUser = user;
        this.telefono = user.telefono;
        this.direccion = user.direccion;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar el usuario', err);
        this.loading = false;
      }
    });
  }

  updateProfile() {
    if (!this.currentUser) return;

    this.loading = true;
    const updatedData = { telefono: this.telefono, direccion: this.direccion };

    this.apiService.updateCurrentUser(updatedData).subscribe({
      next: () => {
        this.loadCurrentUser(); // Recargar datos
      },
      error: (err) => console.error('Error al actualizar perfil', err),
      complete: () => this.loading = false
    });
  }

  changePassword() {
    this.passwordChangeErrorMessage = null; // Limpiar mensajes de error previos
    this.passwordChangeLoading = true; // Activar estado de carga

    if (this.newPassword !== this.confirmPassword) {
      this.passwordChangeErrorMessage = 'Las contraseñas no coinciden.';
      this.passwordChangeLoading = false;
      return;
    }

    if (!this.newPassword || this.newPassword.length < 6) { // Ejemplo de validación simple
      this.passwordChangeErrorMessage = 'La contraseña debe tener al menos 6 caracteres.';
      this.passwordChangeLoading = false;
      return;
    }

    this.apiService.changeProfilePassword(this.newPassword).subscribe({
      next: () => {
        this.closePasswordModal();
        this.newPassword = '';
        this.confirmPassword = '';
        // Opcional: mostrar un toast o mensaje de éxito
      },
      error: (err) => {
        console.error('Error al cambiar la contraseña', err);
        this.passwordChangeErrorMessage = 'Error al cambiar la contraseña. Inténtalo de nuevo.';
        this.passwordChangeLoading = false;
      },
      complete: () => this.passwordChangeLoading = false
    });
  }

  // Lógica para el modal de cambiar contraseña
  openPasswordModal() {
    this.showPasswordModal = true;
    this.passwordChangeErrorMessage = null; // Limpiar errores al abrir el modal
    this.newPassword = ''; // Limpiar campos al abrir el modal
    this.confirmPassword = '';
  }

  closePasswordModal() {
    this.showPasswordModal = false;
  }

  onPasswordChanged() {
    this.showPasswordModal = false;
    // Opcional: mostrar un toast o mensaje de éxito
  }
}
