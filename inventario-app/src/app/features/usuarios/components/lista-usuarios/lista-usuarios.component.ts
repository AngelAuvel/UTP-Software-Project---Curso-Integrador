import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Usuario } from '../../../../modelos/usuario';
import { Page } from '../../../../modelos/page';
import { Rol } from '../../../../modelos/usuario';
import { UsuarioService } from '../../../../core/services/usuario.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { ConfirmationService } from '../../../../core/services/confirmation.service';
import { HasRoleDirective } from '../../../../shared/directives/has-role.directive';
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-lista-usuarios',
  standalone: true,
  imports: [CommonModule, RouterModule, HasRoleDirective],
  templateUrl: './lista-usuarios.component.html',
  styleUrls: ['./lista-usuarios.component.css']
})
export class ListaUsuariosComponent implements OnInit {

  private usuarioService = inject(UsuarioService);
  private notificationService = inject(NotificationService);
  private confirmationService = inject(ConfirmationService);

  public page$: Observable<Page<Usuario>> | undefined;
  private searchTerms = new Subject<string>();

  currentPage = 0;
  pageSize = 10;
  currentQuery = '';
  Rol = Rol; // Exponer el enum a la plantilla

  ngOnInit(): void {
    this.loadUsuarios();

    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => {
        this.currentQuery = term;
        this.currentPage = 0;
        return this.usuarioService.getUsuarios(this.currentPage, this.pageSize, this.currentQuery);
      })
    ).subscribe(page => {
      // El observable page$ se actualizará automáticamente en el template
    });
  }

  loadUsuarios(): void {
    this.page$ = this.usuarioService.getUsuarios(this.currentPage, this.pageSize, this.currentQuery);
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadUsuarios();
  }

  eliminarUsuario(id: number): void {
    this.confirmationService.confirm('Confirmar Eliminación', '¿Está seguro de que desea eliminar este usuario?')
      .subscribe(confirmed => {
        if (confirmed) {
          this.usuarioService.eliminarUsuario(id).subscribe({
            next: () => {
              this.notificationService.showSuccess('Usuario eliminado exitosamente.');
              this.loadUsuarios();
            },
            error: (err) => {
              this.notificationService.showError('Error al eliminar el usuario.');
              console.error(err);
            }
          });
        }
      });
  }
}
