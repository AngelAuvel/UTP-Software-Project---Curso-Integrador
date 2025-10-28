import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../servicios/api.service';
import { Categoria } from '../modelos/categoria';
import { CategoriaFormComponent } from '../categoria-form/categoria-form.component';

@Component({
  selector: 'app-categoria-lista',
  standalone: true,
  imports: [CommonModule, CategoriaFormComponent],
  templateUrl: './categoria-lista.component.html',
})
export class CategoriaListaComponent implements OnInit {
  private apiService = inject(ApiService);

  categorias: Categoria[] = [];
  loading = true;
  showModal = false;
  selectedCategoria: Partial<Categoria> | null = null;

  ngOnInit() {
    this.loadCategorias();
  }

  loadCategorias() {
    this.loading = true;
    this.apiService.getCategories().subscribe(data => {
      this.categorias = data;
      this.loading = false;
    });
  }

  openModal(categoria: Partial<Categoria> | null = null) {
    this.selectedCategoria = categoria ? { ...categoria } : null;
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.selectedCategoria = null;
  }

  saveCategoria(categoria: Partial<Categoria>) {
    const action = categoria.id
      ? this.apiService.updateCategory(categoria.id, categoria)
      : this.apiService.createCategory(categoria);

    action.subscribe(() => {
      this.loadCategorias();
      this.closeModal();
    });
  }

  deleteCategoria(id: number) {
    if (confirm('¿Estás seguro de que quieres eliminar esta categoría?')) {
      this.apiService.deleteCategory(id).subscribe(() => {
        this.loadCategorias();
      });
    }
  }
}
