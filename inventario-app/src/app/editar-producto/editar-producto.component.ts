import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ApiService } from '../servicios/api.service';
import { Producto } from '../modelos/producto';
import { Categoria } from '../modelos/categoria';
import { Proveedor } from '../modelos/proveedor';

@Component({
  selector: 'app-editar-producto',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './editar-producto.html', // Corregido
})
export class EditarProductoComponent implements OnInit {
  private apiService = inject(ApiService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  producto: Producto | null = null;
  categorias: Categoria[] = [];
  proveedores: Proveedor[] = [];
  loading = true;
  productoId!: number;

  ngOnInit() {
    this.productoId = this.route.snapshot.params['id'];
    this.loadCategoriasYProveedores();
    this.loadProducto();
  }

  loadCategoriasYProveedores() {
    this.apiService.getCategories().subscribe((cats: Categoria[]) => this.categorias = cats);
    this.apiService.getProveedores().subscribe((provs: Proveedor[]) => this.proveedores = provs); // Corregido
  }

  loadProducto() {
    this.loading = true;
    this.apiService.getProductById(this.productoId).subscribe({
      next: (data: Producto) => {
        this.producto = data;
        this.loading = false;
      },
      error: (err: any) => {
        console.error('Error al cargar producto', err);
        this.loading = false;
      }
    });
  }

  updateProducto() {
    if (!this.producto) return;

    this.loading = true;
    this.apiService.updateProduct(this.productoId, this.producto).subscribe({
      next: () => {
        this.router.navigate(['/productos']);
      },
      error: (err: any) => {
        console.error('Error al actualizar producto', err);
        this.loading = false;
      }
    });
  }
}
