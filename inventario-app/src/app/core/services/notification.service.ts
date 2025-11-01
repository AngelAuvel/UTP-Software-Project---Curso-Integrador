import { Injectable, inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private toastr = inject(ToastrService);

  showSuccess(message: string, title: string = 'Éxito'): void {
    this.toastr.success(message, title);
  }

  showError(message: string, title: string = 'Error'): void {
    this.toastr.error(message, title);
  }

  showInfo(message: string, title: string = 'Información'): void {
    this.toastr.info(message, title);
  }

  showWarning(message: string, title: string = 'Advertencia'): void {
    this.toastr.warning(message, title);
  }
}
