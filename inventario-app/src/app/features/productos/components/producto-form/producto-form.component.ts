import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductoService } from '../../../../core/services/producto.service';
import { CategoriaService } from '../../../../core/services/categoria.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { Categoria, Producto, UnidadMedida } from '../../../../modelos/producto';
import { FormControlErrorComponent } from '../../../../shared/components/form-control-error/form-control-error.component';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-producto-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, FormControlErrorComponent],
  templateUrl: './producto-form.component.html',
  styleUrls: ['./producto-form.component.css']
})
export class ProductoFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private productoService = inject(ProductoService);
  private categoriaService = inject(CategoriaService);
  private notificationService = inject(NotificationService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  productoForm = this.fb.group({
    codigo: ['', Validators.required],
    nombre: ['', Validators.required],
    descripcion: [''],
    categoriaId: [null as number | null, Validators.required],
    unidadMedida: [null as UnidadMedida | null, Validators.required],
    precioUnitario: [0, [Validators.required, Validators.min(0)]],
    stockMinimo: [0, [Validators.required, Validators.min(0)]],
    stockMaximo: [0, [Validators.required, Validators.min(0)]]
  });

  categorias$: Observable<Categoria[]> | undefined;
  unidadesMedida = Object.values(UnidadMedida);
  productoId: number | null = null;
  isEditMode = false;

  ngOnInit(): void {
    this.categorias$ = this.categoriaService.getCategorias();
    const idParam = this.route.snapshot.paramMap.get('id');
    this.productoId = idParam ? +idParam : null;
    this.isEditMode = !!this.productoId;

    if (this.isEditMode && this.productoId) {
      this.productoService.getProducto(this.productoId).subscribe(producto => {
        this.productoForm.patchValue({
          ...producto,
          categoriaId: producto.categoria.id
        });
      });
    }
  }

  onSubmit(): void {
    if (this.productoForm.invalid) {
      this.productoForm.markAllAsTouched();
      this.notificationService.showWarning('Por favor, complete todos los campos requeridos.');
      return;
    }

    const formValue = this.productoForm.value;
    const productoPayload: Partial<Producto> = {
      codigo: formValue.codigo!,
      nombre: formValue.nombre!,
      descripcion: formValue.descripcion!,
      categoria: { id: formValue.categoriaId! } as Categoria,
      unidadMedida: formValue.unidadMedida!,
      precioUnitario: formValue.precioUnitario!,
      stockMinimo: formValue.stockMinimo!,
      stockMaximo: formValue.stockMaximo!
    };

    const action = this.isEditMode
      ? this.productoService.actualizarProducto(this.productoId!, productoPayload as Producto)
      : this.productoService.crearProducto(productoPayload as Producto);

    action.subscribe({
      next: () => {
        const message = this.isEditMode ? 'Producto actualizado exitosamente.' : 'Producto creado exitosamente.';
        this.notificationService.showSuccess(message);
        this.router.navigate(['/productos']);
      },
      error: (err) => {
        this.notificationService.showError('Ocurrió un error al guardar el producto.');
        console.error(err);
      }
    });
  }
}
