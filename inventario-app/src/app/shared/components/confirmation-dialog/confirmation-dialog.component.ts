import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmationService } from '../../../core/services/confirmation.service';

@Component({
  selector: 'app-confirmation-dialog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.css']
})
export class ConfirmationDialogComponent {
  confirmationService = inject(ConfirmationService);

  onConfirm(state: any): void {
    if (state.resolve) {
      state.resolve(true);
    }
  }

  onCancel(state: any): void {
    if (state.resolve) {
      state.resolve(false);
    }
  }
}
