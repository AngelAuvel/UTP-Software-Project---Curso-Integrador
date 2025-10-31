import { HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { Auth } from '../servicios/auth'; // Asegúrate de usar la ruta correcta

/**
 * Función interceptora para adjuntar el token JWT a las peticiones HTTP salientes.
 * @param req La solicitud HTTP original.
 * @param next La función para continuar con la solicitud.
 * @returns Un Observable de eventos HTTP.
 */
export function jwtInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const authService = inject(Auth);
  const token = authService.getToken();

  // Si hay un token, clonamos la solicitud y le añadimos el encabezado Authorization
  if (token) {
    const cloned = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });

    // Pasamos la solicitud clonada (con el token) a la cadena de manejo
    return next(cloned);
  }

  // Si no hay token, simplemente continuamos con la solicitud original (para rutas públicas como /login)
  return next(req);
}