import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-form-control-error',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './form-control-error.component.html',
  styleUrls: ['./form-control-error.component.css']
})
export class FormControlErrorComponent {
  @Input() control: AbstractControl | null = null;

  get errorMessage(): string | null {
    if (!this.control || !this.control.touched || !this.control.errors) {
      return null;
    }

    if (this.control.hasError('required')) {
      return 'Este campo es requerido.';
    }
    if (this.control.hasError('email')) {
      return 'El formato del correo electrónico no es válido.';
    }
    if (this.control.hasError('min')) {
      return `El valor mínimo es ${this.control.errors['min'].min}.`;
    }
    // Se pueden añadir más validaciones personalizadas aquí

    return 'El campo contiene errores.';
  }
}
