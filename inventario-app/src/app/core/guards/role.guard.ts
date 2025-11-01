import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Rol } from '../../modelos/usuario';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const expectedRoles = route.data['roles'] as Rol[];

  if (!expectedRoles || expectedRoles.length === 0) {
    return true; // Si no se esperan roles, se permite el acceso
  }

  const userHasRole = expectedRoles.some(role => authService.hasRole(role));

  if (userHasRole) {
    return true;
  }

  // Redirige al dashboard si el usuario no tiene el rol requerido
  return router.parseUrl('/dashboard');
};
