import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ApiService } from '../servicios/api.service';
import { Categoria } from '../modelos/categoria';
import { Proveedor } from '../modelos/proveedor';

@Component({
  selector: 'app-agregar-producto',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './agregar-producto.html',
})
export class AgregarProducto implements OnInit {
  private apiService = inject(ApiService);
  private router = inject(Router);

  producto = {
    nombre: '',
    categoriaId: null,
    cantidadStock: 0,
    stockMinimo: 0,
    precio: 0,
    codigo: '',
    proveedorId: null,
    descripcion: '',
    estado: 'ACTIVO'
  };
  categorias: Categoria[] = [];
  proveedores: Proveedor[] = [];
  loading = false;

  ngOnInit() {
    this.loadCategoriasYProveedores();
  }

  loadCategoriasYProveedores() {
    this.loading = true;
    this.apiService.getCategories().subscribe((cats: Categoria[]) => this.categorias = cats);
    this.apiService.getProveedores().subscribe((provs: Proveedor[]) => { // Corregido
      this.proveedores = provs;
      this.loading = false;
    });
  }

  createProducto() { // Corregido
    this.loading = true;
    this.apiService.createProduct(this.producto).subscribe({
      next: () => {
        this.router.navigate(['/productos']);
      },
      error: (err: any) => {
        console.error('Error al crear producto', err);
        this.loading = false;
      }
    });
  }

  cancelar() {
    this.router.navigate(['/productos']);
  }
}
