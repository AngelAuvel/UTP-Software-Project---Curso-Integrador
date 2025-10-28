import { Component, EventEmitter, inject, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../servicios/api.service';
import { Producto } from '../modelos/producto';
import { Categoria } from '../modelos/categoria';
import { Proveedor } from '../modelos/proveedor';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-form.component.html',
})
export class ProductFormComponent implements OnInit, OnChanges {
  @Input() product: Partial<Producto> | null = null;
  @Output() save = new EventEmitter<Partial<Producto>>();
  @Output() close = new EventEmitter<void>();

  private apiService = inject(ApiService);

  // Usamos un clon para no modificar el objeto original directamente
  editableProduct: Partial<Producto> = {};
  isEditMode = false;
  loading = false;

  categorias: Categoria[] = [];
  proveedores: Proveedor[] = [];

  ngOnInit() {
    this.loadCatalogos();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['product'] && this.product) {
      // Clonar el producto para edición segura
      this.editableProduct = { ...this.product };
      this.isEditMode = !!this.editableProduct.id;
      this.calculateUnitPrice(); // Calcular al cargar un producto existente
    } else {
      this.resetForm();
    }
  }

  loadCatalogos() {
    this.apiService.getCategories().subscribe(data => this.categorias = data);
    this.apiService.getProveedores().subscribe(data => this.proveedores = data);
  }

  resetForm() {
    this.editableProduct = {
      nombre: '',
      categoriaId: null,
      cantidadStock: 0,
      stockMinimo: 0,
      precio: 0,
      precioTotal: 0,
      codigo: '',
      proveedorId: null,
      descripcion: '',
      estado: 'ACTIVO'
    };
    this.isEditMode = false;
  }

  calculateUnitPrice() {
    const total = this.editableProduct.precioTotal ?? 0;
    const quantity = this.editableProduct.cantidadStock ?? 0;

    if (quantity > 0) {
      this.editableProduct.precio = parseFloat((total / quantity).toFixed(2));
    } else {
      this.editableProduct.precio = 0;
    }
  }

  onSave() {
    this.save.emit(this.editableProduct);
  }

  onClose() {
    this.close.emit();
  }
}
