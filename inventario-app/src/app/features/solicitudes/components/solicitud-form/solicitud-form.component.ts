import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { SolicitudService } from '../../../../core/services/solicitud.service';
import { ProductoService } from '../../../../core/services/producto.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { ConfirmationService } from '../../../../core/services/confirmation.service';
import { InputDialogService } from '../../../../core/services/input-dialog.service';
import { Solicitud, PrioridadSolicitud, EstadoSolicitud } from '../../../../modelos/solicitud';
import { Producto } from '../../../../modelos/producto';
import { Departamento } from '../../../../modelos/departamento';
import { Rol } from '../../../../modelos/usuario';
import { HasRoleDirective } from '../../../../shared/directives/has-role.directive';
import { Observable } from 'rxjs';
import { AuthService } from '../../../../core/services/auth.service'; // Importar AuthService

@Component({
  selector: 'app-solicitud-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, HasRoleDirective],
  templateUrl: './solicitud-form.component.html',
  styleUrls: ['./solicitud-form.component.css']
})
export class SolicitudFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private solicitudService = inject(SolicitudService);
  private productoService = inject(ProductoService);
  private notificationService = inject(NotificationService);
  private confirmationService = inject(ConfirmationService);
  private inputDialogService = inject(InputDialogService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private authService = inject(AuthService); // Inyectar AuthService

  solicitudForm: FormGroup;
  productos$: Observable<Producto[]> | undefined;
  prioridades = Object.values(PrioridadSolicitud);

  solicitud: Solicitud | null = null;
  solicitudId: number | null = null;
  isEditMode = false;
  Rol = Rol; // Exponer el enum a la plantilla

  userDepartmentName: string | null = null; // Para mostrar el nombre del departamento del usuario

  constructor() {
    this.solicitudForm = this.fb.group({
      // departamento: [null, Validators.required], // Eliminado
      fechaRequerida: ['', Validators.required],
      prioridad: [PrioridadSolicitud.NORMAL, Validators.required],
      justificacion: [''],
      detalles: this.fb.array([], Validators.required)
    });
  }

  ngOnInit(): void {
    // this.departamentos$ = this.departamentoService.getDepartamentos(); // Eliminado
    this.productos$ = this.productoService.getAllProductos();

    // Obtener el nombre del departamento del usuario logueado
    this.userDepartmentName = this.authService.userDepartamentoNombre();

    const idParam = this.route.snapshot.paramMap.get('id');
    this.solicitudId = idParam ? +idParam : null;
    this.isEditMode = !!this.solicitudId;

    if (this.isEditMode && this.solicitudId) {
      this.solicitudService.getSolicitud(this.solicitudId).subscribe(solicitud => {
        this.solicitud = solicitud;
        this.solicitudForm.patchValue(solicitud);
        solicitud.detalles.forEach(detalle => {
          this.detalles.push(this.fb.group({
            producto: [detalle.producto, Validators.required],
            cantidadSolicitada: [detalle.cantidadSolicitada, [Validators.required, Validators.min(1)]],
            especificaciones: [detalle.especificaciones]
          }));
        });
        this.solicitudForm.disable();
      });
    }
  }

  get detalles(): FormArray {
    return this.solicitudForm.get('detalles') as FormArray;
  }

  agregarProducto(): void {
    const detalleForm = this.fb.group({
      producto: [null, Validators.required],
      cantidadSolicitada: [1, [Validators.required, Validators.min(1)]],
      especificaciones: ['']
    });
    this.detalles.push(detalleForm);
  }

  removerProducto(index: number): void {
    this.detalles.removeAt(index);
  }

  onSubmit(): void {
    if (this.solicitudForm.invalid) {
      this.notificationService.showWarning('Por favor, complete todos los campos requeridos.');
      return;
    }

    // Construir el objeto de solicitud para enviar al backend
    const solicitudPayload = {
      ...this.solicitudForm.value,
      departamentoId: this.authService.userDepartamentoId() // Añadir el departamentoId del usuario logueado
    };

    this.solicitudService.crearSolicitud(solicitudPayload).subscribe({
      next: () => {
        this.notificationService.showSuccess('Solicitud creada exitosamente.');
        this.router.navigate(['/solicitudes']);
      },
      error: (err) => {
        this.notificationService.showError('Ocurrió un error al crear la solicitud.');
        console.error(err);
      }
    });
  }

  // Métodos para acciones de cambio de estado

  aprobar(): void {
    this.confirmationService.confirm('Aprobar Solicitud', '¿Está seguro de que desea aprobar esta solicitud?').subscribe(confirmed => {
      if (confirmed) {
        this.solicitudService.aprobarSolicitud(this.solicitudId!).subscribe(updated => {
          this.solicitud = updated;
          this.notificationService.showSuccess('Solicitud aprobada.');
        });
      }
    });
  }

  rechazar(): void {
    this.inputDialogService.prompt('Rechazar Solicitud', 'Por favor, ingrese el motivo del rechazo:').subscribe(motivo => {
      if (motivo) {
        this.solicitudService.rechazarSolicitud(this.solicitudId!, motivo).subscribe(updated => {
          this.solicitud = updated;
          this.notificationService.showWarning('Solicitud rechazada.');
        });
      }
    });
  }

  despachar(): void {
    this.confirmationService.confirm('Despachar Solicitud', '¿Confirma que los productos están listos para ser despachados?').subscribe(confirmed => {
      if (confirmed) {
        this.solicitudService.despacharSolicitud(this.solicitudId!).subscribe(updated => {
          this.solicitud = updated;
          this.notificationService.showInfo('Solicitud despachada.');
        });
      }
    });
  }

  recibir(): void {
    this.confirmationService.confirm('Confirmar Recepción', '¿Confirma que ha recibido los productos de esta solicitud?').subscribe(confirmed => {
      if (confirmed) {
        this.solicitudService.recibirSolicitud(this.solicitudId!).subscribe(updated => {
          this.solicitud = updated;
          this.notificationService.showSuccess('Solicitud recibida y completada.');
        });
      }
    });
  }
}
