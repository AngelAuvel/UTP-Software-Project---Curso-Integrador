import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { UsuarioService } from '../../../../core/services/usuario.service';
import { DepartamentoService } from '../../../../core/services/departamento.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { Usuario, Rol } from '../../../../modelos/usuario';
import { Departamento } from '../../../../modelos/departamento';
import { FormControlErrorComponent } from '../../../../shared/components/form-control-error/form-control-error.component';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, FormControlErrorComponent],
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
})
export class UsuarioFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private usuarioService = inject(UsuarioService);
  private departamentoService = inject(DepartamentoService);
  private notificationService = inject(NotificationService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  usuarioForm = this.fb.group({
    nombre: ['', Validators.required],
    apellido: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: [''], // La contraseña es opcional en la edición
    departamentoId: [null as number | null, Validators.required],
    puesto: [''],
    rol: [null as Rol | null, Validators.required],
    estado: ['Activo', Validators.required]
  });

  departamentos$: Observable<Departamento[]> | undefined;
  roles = Object.values(Rol);
  usuarioId: number | null = null;
  isEditMode = false;

  ngOnInit(): void {
    this.departamentos$ = this.departamentoService.getDepartamentos();
    const idParam = this.route.snapshot.paramMap.get('id');
    this.usuarioId = idParam ? +idParam : null;
    this.isEditMode = !!this.usuarioId;

    if (this.isEditMode) {
      this.usuarioForm.get('password')?.clearValidators();
      this.usuarioService.getUsuario(this.usuarioId!).subscribe(usuario => {
        this.usuarioForm.patchValue({
          ...usuario,
          departamentoId: usuario.departamento?.id || null
        });
      });
    } else {
      this.usuarioForm.get('password')?.setValidators(Validators.required);
    }
  }

  onSubmit(): void {
    if (this.usuarioForm.invalid) {
      this.usuarioForm.markAllAsTouched();
      this.notificationService.showWarning('Por favor, complete todos los campos requeridos.');
      return;
    }

    const formValue = this.usuarioForm.value;
    const usuarioPayload: Partial<Usuario> = {
      nombre: formValue.nombre!,
      apellido: formValue.apellido!,
      email: formValue.email!,
      departamento: { id: formValue.departamentoId! } as Departamento,
      puesto: formValue.puesto!,
      rol: formValue.rol!,
      estado: formValue.estado!
    };

    if (formValue.password) {
      (usuarioPayload as any).password = formValue.password;
    }

    const action = this.isEditMode
      ? this.usuarioService.actualizarUsuario(this.usuarioId!, usuarioPayload as Usuario)
      : this.usuarioService.crearUsuario(usuarioPayload as Usuario);

    action.subscribe({
      next: () => {
        const message = this.isEditMode ? 'Usuario actualizado exitosamente.' : 'Usuario creado exitosamente.';
        this.notificationService.showSuccess(message);
        this.router.navigate(['/usuarios']);
      },
      error: (err) => {
        this.notificationService.showError('Ocurrió un error al guardar el usuario.');
        console.error(err);
      }
    });
  }
}
