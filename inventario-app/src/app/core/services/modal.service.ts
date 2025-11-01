import { Injectable, Type } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  // Usaremos un BehaviorSubject simple. Es suficiente.
  public isModalOpen$ = new BehaviorSubject<boolean>(false);
  public component$ = new BehaviorSubject<Type<any> | null>(null);

  open(component: Type<any>): void {
    this.component$.next(component);
    this.isModalOpen$.next(true);
  }

  close(): void {
    this.isModalOpen$.next(false);
    this.component$.next(null);
  }
}
