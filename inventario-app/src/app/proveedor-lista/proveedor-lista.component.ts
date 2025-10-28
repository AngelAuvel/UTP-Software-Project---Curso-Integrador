import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../servicios/api.service';
import { Proveedor } from '../modelos/proveedor';
import { ProveedorFormComponent } from '../proveedor-form/proveedor-form.component';
import { Usuario } from '../modelos/usuario';

@Component({
  selector: 'app-proveedor-lista',
  standalone: true,
  imports: [CommonModule, ProveedorFormComponent],
  templateUrl: './proveedor-lista.component.html',
})
export class ProveedorListaComponent implements OnInit {
  private apiService = inject(ApiService);

  proveedores: Proveedor[] = [];
  currentUser: Usuario | null = null;
  loading = true;

  // Estados para el modal
  showProveedorForm = false;
  selectedProveedor: Partial<Proveedor> | null = null;

  ngOnInit() {
    this.apiService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
    });
    this.loadProveedores();
  }

  loadProveedores() {
    this.loading = true;
    this.apiService.getProveedores().subscribe({
      next: (data) => {
        this.proveedores = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar proveedores', err);
        this.loading = false;
      }
    });
  }

  get canManageProveedores(): boolean {
    return this.currentUser?.rol === 'ADMIN' || this.currentUser?.rol === 'LOGISTICA';
  }

  openCreateForm() {
    this.selectedProveedor = null;
    this.showProveedorForm = true;
  }

  openEditForm(proveedor: Proveedor) {
    this.selectedProveedor = { ...proveedor };
    this.showProveedorForm = true;
  }

  closeForm() {
    this.showProveedorForm = false;
    this.selectedProveedor = null;
  }

  saveProveedor(proveedor: Partial<Proveedor>) {
    const operation = proveedor.id
      ? this.apiService.updateProveedor(proveedor.id, proveedor)
      : this.apiService.createProveedor(proveedor);

    operation.subscribe({
      next: () => {
        this.loadProveedores();
        this.closeForm();
      },
      error: (err) => console.error('Error al guardar el proveedor', err)
    });
  }

  deleteProveedor(proveedorId: number) {
    if (!confirm('¿Está seguro de eliminar este proveedor?')) return;

    this.apiService.deleteProveedor(proveedorId).subscribe({
      next: () => {
        this.loadProveedores();
      },
      error: (err) => console.error('Error al eliminar proveedor', err)
    });
  }
}
