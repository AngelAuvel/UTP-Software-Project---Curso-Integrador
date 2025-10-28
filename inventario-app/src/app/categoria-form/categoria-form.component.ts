import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Categoria } from '../modelos/categoria';

@Component({
  selector: 'app-categoria-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './categoria-form.component.html',
})
export class CategoriaFormComponent implements OnChanges {
  @Input() categoria: Partial<Categoria> | null = null;
  @Output() save = new EventEmitter<Partial<Categoria>>();
  @Output() close = new EventEmitter<void>();

  editableCategoria: Partial<Categoria> = {};
  isEditMode = false;

  ngOnChanges(changes: SimpleChanges) {
    if (changes['categoria'] && this.categoria) {
      this.editableCategoria = { ...this.categoria };
      this.isEditMode = !!this.editableCategoria.id;
    } else {
      this.resetForm();
    }
  }

  resetForm() {
    this.editableCategoria = {
      nombre: '',
      descripcion: ''
    };
    this.isEditMode = false;
  }

  onSave() {
    this.save.emit(this.editableCategoria);
  }

  onClose() {
    this.close.emit();
  }
}
