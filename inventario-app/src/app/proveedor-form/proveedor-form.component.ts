import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Proveedor } from '../modelos/proveedor';

@Component({
  selector: 'app-proveedor-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './proveedor-form.component.html',
})
export class ProveedorFormComponent implements OnChanges {
  @Input() proveedor: Partial<Proveedor> | null = null;
  @Output() save = new EventEmitter<Partial<Proveedor>>();
  @Output() close = new EventEmitter<void>();

  editableProveedor: Partial<Proveedor> = {};
  isEditMode = false;
  loading = false;

  ngOnChanges(changes: SimpleChanges) {
    if (changes['proveedor'] && this.proveedor) {
      this.editableProveedor = { ...this.proveedor };
      this.isEditMode = !!this.editableProveedor.id;
    } else {
      this.resetForm();
    }
  }

  resetForm() {
    this.editableProveedor = {
      nombre: '',
      ruc: '',
      telefono: '',
      direccion: '',
      estado: 'ACTIVO'
    };
    this.isEditMode = false;
  }

  onSave() {
    this.save.emit(this.editableProveedor);
  }

  onClose() {
    this.close.emit();
  }
}
