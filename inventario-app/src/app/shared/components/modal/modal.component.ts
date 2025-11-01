import { Component, inject, OnDestroy, Type } from '@angular/core';
import { CommonModule, NgComponentOutlet } from '@angular/common'; // Importar NgComponentOutlet
import { ModalService } from '../../../core/services/modal.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule, NgComponentOutlet], // Añadir NgComponentOutlet aquí
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnDestroy {

  modalService = inject(ModalService);
  isModalOpen$ = this.modalService.isModalOpen$;
  currentDynamicComponent: Type<any> | null = null;
  private componentSubscription: Subscription;

  constructor() {
    this.componentSubscription = this.modalService.component$.subscribe(component => {
      this.currentDynamicComponent = component;
    });
  }

  ngOnDestroy(): void {
    this.componentSubscription.unsubscribe();
  }

  closeModal(): void {
    // Este método no se usará para el modal de cambio de contraseña forzado
  }
}
