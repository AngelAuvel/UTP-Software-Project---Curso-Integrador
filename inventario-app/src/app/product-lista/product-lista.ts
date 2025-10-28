import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../servicios/api.service';
import { Producto } from '../modelos/producto';
import { Usuario } from '../modelos/usuario';
import { ProductFormComponent } from '../product-form/product-form.component';

@Component({
  selector: 'app-product-lista',
  standalone: true,
  imports: [CommonModule, FormsModule, ProductFormComponent],
  templateUrl: './product-lista.html',
})
export class ProductLista implements OnInit {
  private apiService = inject(ApiService);

  products: Producto[] = [];
  filteredProducts: Producto[] = [];
  currentUser: Usuario | null = null;
  loading = true;
  searchTerm = '';

  // Estados para el modal
  showProductForm = false;
  selectedProduct: Partial<Producto> | null = null;

  ngOnInit() {
    this.apiService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
    });
    this.loadProducts();
  }

  loadProducts() {
    this.loading = true;
    this.apiService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.filteredProducts = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar productos', err);
        this.loading = false;
      }
    });
  }

  searchProducts() {
    this.filteredProducts = this.products.filter(p =>
      p.nombre.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      p.codigo.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      (p.categoriaNombre && p.categoriaNombre.toLowerCase().includes(this.searchTerm.toLowerCase()))
    );
  }

  get canManageProducts(): boolean {
    return this.currentUser?.rol === 'ADMIN' || this.currentUser?.rol === 'LOGISTICA';
  }

  openCreateForm() {
    this.selectedProduct = null; // No hay producto seleccionado para crear uno nuevo
    this.showProductForm = true;
  }

  openEditForm(product: Producto) {
    this.selectedProduct = { ...product }; // Clonar para evitar mutaciones no deseadas
    this.showProductForm = true;
  }

  closeForm() {
    this.showProductForm = false;
    this.selectedProduct = null;
  }

  saveProduct(product: Partial<Producto>) {
    const operation = product.id
      ? this.apiService.updateProduct(product.id, product)
      : this.apiService.createProduct(product);

    operation.subscribe({
      next: () => {
        this.loadProducts();
        this.closeForm();
      },
      error: (err) => console.error('Error al guardar el producto', err)
    });
  }

  deleteProduct(productId: number) {
    if (!confirm('¿Está seguro de eliminar este producto?')) return;

    this.apiService.deleteProduct(productId).subscribe({
      next: () => {
        this.loadProducts();
      },
      error: (err) => console.error('Error al eliminar producto', err)
    });
  }
}
