import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ApiService } from '../servicios/api.service';
import { Proveedor } from '../modelos/proveedor';

@Component({
  selector: 'app-editar-proveedor',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './editar-proveedor.component.html',
})
export class EditarProveedorComponent implements OnInit {
  private apiService = inject(ApiService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  proveedor: Proveedor | null = null;
  loading = true;
  proveedorId!: number;

  ngOnInit() {
    this.proveedorId = this.route.snapshot.params['id'];
    this.loadProveedor();
  }

  loadProveedor() {
    this.loading = true;
    this.apiService.getProveedorById(this.proveedorId).subscribe({
      next: (data) => {
        this.proveedor = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar proveedor', err);
        this.loading = false;
      }
    });
  }

  updateProveedor() {
    if (!this.proveedor) return;

    this.loading = true;
    this.apiService.updateProveedor(this.proveedorId, this.proveedor).subscribe({
      next: () => {
        this.router.navigate(['/proveedores']);
      },
      error: (err) => {
        console.error('Error al actualizar proveedor', err);
        this.loading = false;
      }
    });
  }
}
