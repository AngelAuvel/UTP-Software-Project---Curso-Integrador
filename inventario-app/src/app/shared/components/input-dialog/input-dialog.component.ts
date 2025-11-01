import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InputDialogService } from '../../../core/services/input-dialog.service';

@Component({
  selector: 'app-input-dialog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './input-dialog.component.html',
  styleUrls: ['./input-dialog.component.css']
})
export class InputDialogComponent {
  inputDialogService = inject(InputDialogService);
  inputValue: string = '';

  onConfirm(state: any): void {
    state.resolve(this.inputValue);
    this.inputValue = '';
  }

  onCancel(state: any): void {
    state.resolve(null);
    this.inputValue = '';
  }
}
