import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ApiService } from '../servicios/api.service';

@Component({
  selector: 'app-agregar-usuario',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './agregar-usuario.component.html',
})
export class AgregarUsuarioComponent {
  private apiService = inject(ApiService);
  private router = inject(Router);

  usuario = {
    nombres: '',
    apellidos: '',
    correo: '',
    password: '',
    rol: 'USER',
    dni: '',
    telefono: '',
    direccion: '',
    area: '',
  };
  loading = false;

  registerUsuario() {
    this.loading = true;
    this.apiService.register(this.usuario).subscribe({
      next: () => {
        this.router.navigate(['/usuarios']);
      },
      error: (err) => {
        console.error('Error al registrar usuario', err);
        this.loading = false;
      }
    });
  }
}
