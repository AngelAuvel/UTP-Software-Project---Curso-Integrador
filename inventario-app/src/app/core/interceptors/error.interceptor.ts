import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { ModalService } from '../services/modal.service';
import { PasswordChangeComponent } from '../../shared/components/password-change/password-change.component';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const modalService = inject(ModalService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      // Comprobamos si el cuerpo del error existe y tiene la propiedad que buscamos
      if (error.status === 403 && error.error?.error === 'FORCE_PASSWORD_CHANGE') {
        console.log('Interceptor: Se detectó FORCE_PASSWORD_CHANGE. Abriendo modal...');
        modalService.open(PasswordChangeComponent);
      } else {
        // Para otros errores, simplemente los relanzamos
        console.error('Interceptor: Ocurrió un error HTTP', error);
      }
      return throwError(() => error);
    })
  );
};
