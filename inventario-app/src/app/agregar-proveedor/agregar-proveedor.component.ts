import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ApiService } from '../servicios/api.service';

@Component({
  selector: 'app-agregar-proveedor',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './agregar-proveedor.component.html',
})
export class AgregarProveedorComponent {
  private apiService = inject(ApiService);
  private router = inject(Router);

  proveedor = {
    nombre: '',
    ruc: '',
    telefono: '',
    direccion: '',
    estado: 'ACTIVO'
  };
  loading = false;

  createProveedor() {
    this.loading = true;
    this.apiService.createProveedor(this.proveedor).subscribe({
      next: () => {
        this.router.navigate(['/proveedores']);
      },
      error: (err) => {
        console.error('Error al crear proveedor', err);
        this.loading = false;
      }
    });
  }
}
