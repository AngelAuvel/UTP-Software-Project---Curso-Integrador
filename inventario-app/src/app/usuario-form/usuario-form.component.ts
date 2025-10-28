import { Component, Input, Output, EventEmitter, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../modelos/usuario';
import { Area } from '../modelos/area';
import { ApiService } from '../servicios/api.service';

// Definimos un tipo local para el formulario que incluye la contraseña
type UserFormData = Partial<Usuario> & { password?: string };

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuario-form.component.html',
})
export class UsuarioFormComponent implements OnInit {
  // Usamos el tipo local UserFormData
  @Input() user: UserFormData | null = null;
  @Output() save = new EventEmitter<UserFormData>();
  @Output() close = new EventEmitter<void>();

  private apiService = inject(ApiService);

  isEditMode = false;
  availableAreas: Area[] = [];

  ngOnInit() {
    this.loadAreas();
    if (this.user && this.user.idUsuario) {
      this.isEditMode = true;
    } else {
      this.isEditMode = false;
      // Inicializamos con el modelo completo, incluyendo la contraseña
      this.user = {
        nombres: '',
        apellidos: '',
        correo: '',
        password: '',
        rol: 'USUARIO',
        dni: '',
        telefono: '',
        direccion: '',
        area: '',
      };
    }
  }

  loadAreas() {
    this.apiService.getAreas().subscribe(data => {
      this.availableAreas = data;
    });
  }

  saveUser() {
    if (this.user) {
      this.save.emit(this.user);
    }
  }

  closeModal() {
    this.close.emit();
  }
}
