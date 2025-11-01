import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { HasRoleDirective } from '../../directives/has-role.directive';
import { Rol } from '../../../modelos/usuario';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterModule, HasRoleDirective],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  Rol = Rol; // Exponer el enum a la plantilla

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
