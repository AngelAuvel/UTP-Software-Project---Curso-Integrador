import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Producto } from '../../../../modelos/producto';
import { Page } from '../../../../modelos/page';
import { Rol } from '../../../../modelos/usuario';
import { ProductoService } from '../../../../core/services/producto.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { ConfirmationService } from '../../../../core/services/confirmation.service';
import { HasRoleDirective } from '../../../../shared/directives/has-role.directive';
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-lista-productos',
  standalone: true,
  imports: [CommonModule, RouterModule, HasRoleDirective],
  templateUrl: './lista-productos.component.html',
  styleUrls: ['./lista-productos.component.css']
})
export class ListaProductosComponent implements OnInit {

  private productoService = inject(ProductoService);
  private notificationService = inject(NotificationService);
  private confirmationService = inject(ConfirmationService);

  public page$: Observable<Page<Producto>> | undefined;
  private searchTerms = new Subject<string>();

  currentPage = 0;
  pageSize = 10;
  currentQuery = '';
  Rol = Rol; // Exponer el enum a la plantilla

  ngOnInit(): void {
    this.loadProductos();

    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => {
        this.currentQuery = term;
        this.currentPage = 0;
        return this.productoService.getProductos(this.currentPage, this.pageSize, this.currentQuery);
      })
    ).subscribe(); // No necesitamos hacer nada en el subscribe aquí
  }

  loadProductos(): void {
    this.page$ = this.productoService.getProductos(this.currentPage, this.pageSize, this.currentQuery);
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadProductos();
  }

  eliminarProducto(id: number): void {
    this.confirmationService.confirm('Confirmar Eliminación', '¿Está seguro de que desea eliminar este producto?')
      .subscribe(confirmed => {
        if (confirmed) {
          this.productoService.eliminarProducto(id).subscribe({
            next: () => {
              this.notificationService.showSuccess('Producto eliminado exitosamente.');
              this.loadProductos();
            },
            error: (err) => {
              this.notificationService.showError('Error al eliminar el producto.');
              console.error(err);
            }
          });
        }
      });
  }
}
