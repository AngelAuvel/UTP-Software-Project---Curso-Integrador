import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../servicios/api.service';
import { Usuario } from '../modelos/usuario';
import { UsuarioFormComponent } from '../usuario-form/usuario-form.component';

@Component({
  selector: 'app-usuario-lista',
  standalone: true,
  imports: [CommonModule, FormsModule, UsuarioFormComponent],
  templateUrl: './usuario-lista.component.html',
})
export class UsuarioListaComponent implements OnInit {
  private apiService = inject(ApiService);

  users: Usuario[] = [];
  filteredUsers: Usuario[] = [];
  currentUser: Usuario | null = null;
  loading = true;
  searchTerm = '';

  // Estados para el modal
  showUserForm = false;
  selectedUser: Partial<Usuario> | null = null;

  ngOnInit() {
    this.apiService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
    });
    this.loadUsers();
  }

  loadUsers() {
    this.loading = true;
    this.apiService.getUsers().subscribe({
      next: (data) => {
        this.users = data;
        this.filteredUsers = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar usuarios', err);
        this.loading = false;
      }
    });
  }

  searchUsers() {
    const lowerCaseSearchTerm = this.searchTerm.toLowerCase();
    this.filteredUsers = this.users.filter(u =>
      (u.nombres && u.nombres.toLowerCase().includes(lowerCaseSearchTerm)) ||
      (u.apellidos && u.apellidos.toLowerCase().includes(lowerCaseSearchTerm)) ||
      (u.correo && u.correo.toLowerCase().includes(lowerCaseSearchTerm)) ||
      (u.dni && u.dni.toLowerCase().includes(lowerCaseSearchTerm)) ||
      (u.telefono && u.telefono.toLowerCase().includes(lowerCaseSearchTerm)) ||
      (u.direccion && u.direccion.toLowerCase().includes(lowerCaseSearchTerm)) ||
      (u.area && u.area.toLowerCase().includes(lowerCaseSearchTerm)) ||
      (u.rol && u.rol.toLowerCase().includes(lowerCaseSearchTerm))
    );
  }

  openCreateForm() {
    this.selectedUser = null;
    this.showUserForm = true;
  }

  openEditForm(user: Usuario) {
    this.selectedUser = { ...user };
    this.showUserForm = true;
  }

  closeForm() {
    this.showUserForm = false;
    this.selectedUser = null;
  }

  saveUser(user: Partial<Usuario>) {
    const operation = user.idUsuario
      ? this.apiService.updateUser(user.idUsuario, user)
      : this.apiService.register(user);

    operation.subscribe({
      next: () => {
        this.loadUsers();
        this.closeForm();
      },
      error: (err) => console.error('Error al guardar el usuario', err)
    });
  }

  deleteUser(userId: number) {
    if (!confirm('¿Está seguro de eliminar este usuario?')) return;

    this.apiService.deleteUser(userId).subscribe({
      next: () => {
        this.loadUsers();
      },
      error: (err) => console.error('Error al eliminar usuario', err)
    });
  }
}
