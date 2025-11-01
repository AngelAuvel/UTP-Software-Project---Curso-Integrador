import { Directive, Input, TemplateRef, ViewContainerRef, inject } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Rol } from '../../modelos/usuario';

@Directive({
  selector: '[appHasRole]',
  standalone: true
})
export class HasRoleDirective {
  private authService = inject(AuthService);
  private templateRef = inject(TemplateRef<any>);
  private viewContainer = inject(ViewContainerRef);

  @Input()
  set appHasRole(roles: Rol[] | Rol) {
    const requiredRoles = Array.isArray(roles) ? roles : [roles];
    const userHasRole = requiredRoles.some(role => this.authService.hasRole(role));

    if (userHasRole) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }
}
