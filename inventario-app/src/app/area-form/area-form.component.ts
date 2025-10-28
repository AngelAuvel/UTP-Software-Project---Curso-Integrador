import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Area } from '../modelos/area';

@Component({
  selector: 'app-area-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './area-form.component.html',
})
export class AreaFormComponent implements OnChanges {
  @Input() area: Partial<Area> | null = null;
  @Output() save = new EventEmitter<Partial<Area>>();
  @Output() close = new EventEmitter<void>();

  editableArea: Partial<Area> = {};
  isEditMode = false;

  ngOnChanges(changes: SimpleChanges) {
    if (changes['area'] && this.area) {
      this.editableArea = { ...this.area };
      this.isEditMode = !!this.editableArea.id;
    } else {
      this.resetForm();
    }
  }

  resetForm() {
    this.editableArea = {
      nombre: ''
    };
    this.isEditMode = false;
  }

  onSave() {
    this.save.emit(this.editableArea);
  }

  onClose() {
    this.close.emit();
  }
}
